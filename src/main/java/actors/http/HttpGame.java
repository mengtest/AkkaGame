package actors.http;

import static akka.pattern.PatternsCS.ask;

import actors.GameManager;
import actors.GameManager.GetGame;
import actors.GameManager.GetGameFailed;
import actors.GameManager.GetGameSucceeded;
import actors.GameManager.JoinGame;
import actors.GameManager.JoinGameFailed;
import actors.GameManager.RemoveUserFromGame;
import actors.GameManager.RemoveUserFromGameFailed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import model.GameApiProtos.GetGameResponse;
import model.GameApiProtos.JoinGameResponse;
import model.GameApiProtos.LeaveGameResponse;
import model.GameProtos.Game;
import utils.ConversionUtils;
import utils.ErrorUtils;
import utils.UserManagerStore;

public class HttpGame extends HttpInterface {
	
	private final ActorRef gameManager;
	private final UserManagerStore userManagerStore;
	
	public HttpGame(ActorSystem system, ActorRef usersManager, String gameId) {
		super(system);
		this.gameManager = system.actorOf(GameManager.props(gameId), "GameManager_" + gameId);
		this.userManagerStore = new UserManagerStore(usersManager);
	}
	
	public Route routes() {
		return get(() -> route(getGameRoute(),
				joinGameRoute(), 
				leaveGameRoute()));
	}
	
	public Route getGameRoute() {
		return path(PathMatchers.segment("game").slash("get"), () -> {
			try {
				Object message = ask(gameManager, new GetGame(), MAX_TIMEOUT)
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
		});
	}
	
	public Route joinGameRoute() {
		return path(PathMatchers.segment("game").slash("join"), () -> 
			parameter("userId", userId -> {
			try {
				userManagerStore.checkIfExists(userId, MAX_TIMEOUT);
				Object message = ask(gameManager, new JoinGame(userManagerStore.get(userId)), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof JoinGameFailed) {
					Throwable cause = ((JoinGameFailed) message).getCause();
					throw cause;
				}
				JoinGameResponse resp = JoinGameResponse.getDefaultInstance();
				return complete(StatusCodes.OK, ConversionUtils.toString(resp));
			} catch (Throwable th) {
				log.error(th, "Game joining failed.");
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}
	
	public Route leaveGameRoute() {
		return path(PathMatchers.segment("game").slash("leave"), () -> 
			parameter("userId", userId -> {
			try {
				userManagerStore.checkIfExists(userId, MAX_TIMEOUT);
				Object message = ask(gameManager, 
							new RemoveUserFromGame(userManagerStore.get(userId), userId), MAX_TIMEOUT)
						.toCompletableFuture().get();
				if (message instanceof RemoveUserFromGameFailed) {
					throw ((RemoveUserFromGameFailed) message).getCause();
				}
				LeaveGameResponse resp = LeaveGameResponse.getDefaultInstance();
				return complete(StatusCodes.OK, ConversionUtils.toString(resp));
			} catch (Throwable th) {
				log.error(th, "Something went wrong when trying leaving the game.");
				return ErrorUtils.errorRoute(this, th);
			}
		}));
	}
}
