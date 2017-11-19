package actors.http;

import static akka.pattern.PatternsCS.ask;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import actors.GameManager.GetGame;
import actors.GameManager.GetGameFailed;
import actors.GameManager.GetGameSucceeded;
import actors.GamesManager;
import actors.GamesManager.CreateGame;
import actors.GamesManager.CreateGameFailed;
import actors.GamesManager.CreateGameSucceeded;
import actors.UserManager.GetUser;
import actors.UserManager.GetUserFailed;
import actors.UserManager.GetUserSucceeded;
import actors.UsersManager;
import actors.UsersManager.CreateUserByUsername;
import actors.UsersManager.CreateUserFailed;
import actors.UsersManager.CreateUserSucceeded;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Identify;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import model.GameApiProtos.CreateGameResponse;
import model.GameApiProtos.GetGameResponse;
import model.GameNotFoundException;
import model.GameProtos.Game;
import model.UserApiProtos.CreateUserResponse;
import model.UserApiProtos.GetUserResponse;
import model.UserProtos.User;
import scala.concurrent.Await;
import scala.concurrent.Future;
import utils.ConversionUtils;
import utils.ErrorUtils;
import utils.UserManagerStore;

public class HttpMain extends HttpInterface {

	private final ActorRef gamesManager;
	private final ActorRef usersManager;
	private final UserManagerStore userManagerStore;
	
	public HttpMain(ActorSystem system) {
		super(system);
		this.usersManager = system.actorOf(UsersManager.props(system), "UsersManager");
		this.gamesManager = system.actorOf(GamesManager.props(), "GamesManager");
		this.userManagerStore = new UserManagerStore(usersManager);
	}

	public Route routes() {
		return get(() -> route(pathSingleSlash(() -> complete("Welcome!")), 
				createUserRoute(), 
				getUserRoute(),
				createGameRoute(),
				getGameRoute()));
	}
	
	public Route createUserRoute() {
		return path(PathMatchers.segment("user").slash("create"), () -> parameter("username", username -> {
			try {
				Object message = ask(usersManager, new CreateUserByUsername(username), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof CreateUserSucceeded) {
					User user = ((CreateUserSucceeded) message).getUser();
					CreateUserResponse resp = CreateUserResponse.newBuilder()
							.setUser(user)
							.build();
					return complete(StatusCodes.CREATED, ConversionUtils.toString(resp));
				} else {
					throw ((CreateUserFailed) message).getCause();
				}
			} catch (Throwable th) {
				log.error(th, "User creation failed.");
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}

	public Route getUserRoute() {
		return path(PathMatchers.segment("user").slash("get"),
				() -> parameter("userId", userId -> {
			try {
				userManagerStore.checkIfExists(userId, MAX_TIMEOUT);
				Object message = ask(userManagerStore.get(userId), new GetUser(), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof GetUserFailed) {
					throw ((GetUserFailed) message).getCause();
				}
				User user = ((GetUserSucceeded) message).getUser();
				GetUserResponse resp = GetUserResponse.newBuilder()
						.setUser(user)
						.build();
				return complete(StatusCodes.OK, ConversionUtils.toString(resp));
			} catch (Throwable th) {
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}
	
	public Route createGameRoute() {
		return path(PathMatchers.segment("game").slash("create"),
				() -> parameter("userId", userId -> {
			try {
				userManagerStore.checkIfExists(userId, MAX_TIMEOUT);
				Object message = ask(gamesManager, new CreateGame(userManagerStore.get(userId)), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof CreateGameFailed) {
					throw ((CreateGameFailed) message).getCause();
				}
				Game game = ((CreateGameSucceeded) message).getGame();
				int port = 8080;
				HttpGame httpGame = new HttpGame(system, usersManager, game.getId());
				while (!httpGame.start(port, null)) {
					port++;
				}
				CreateGameResponse resp = CreateGameResponse.newBuilder()
						.setGame(game)
						.setHost("localhost")
						.setPort(port)
						.build();
				return complete(StatusCodes.CREATED, ConversionUtils.toString(resp));
			} catch (Throwable th) {
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}
	
	public Route getGameRoute() {
		return path(PathMatchers.segment("game").slash("get"),
				() -> parameter("gameId", gameId -> {
			try {
				ActorSelection sel = system.actorSelection("/user/GameManager_" + gameId);
				Timeout t = new Timeout(5, TimeUnit.SECONDS);
			    AskableActorSelection asker = new AskableActorSelection(sel);
			    Future<Object> fut = asker.ask(new Identify(1), t);
			    ActorIdentity ident = (ActorIdentity)Await.result(fut, t.duration());
			    Optional<ActorRef> ref = ident.getActorRef();
			    if (!ref.isPresent()) {
			    	throw new GameNotFoundException("Game with id " + gameId + " doesn't exist.");
			    }
				Object message = ask(ref.get(), new GetGame(), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof GetGameFailed) {
					throw ((GetGameFailed) message).getCause();
				}
				Game game = ((GetGameSucceeded) message).getGame();
				GetGameResponse resp = GetGameResponse.newBuilder()
						.setGame(game)
						.build();
				return complete(StatusCodes.OK, ConversionUtils.toString(resp));
			} catch (Throwable th) {
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}
}
