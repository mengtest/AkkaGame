package actors;

import java.util.Random;

import com.mongodb.MongoClient;

import actors.UserManager.AddToGame;
import actors.UserManager.AddToGameFailed;
import actors.UserManager.AddToGameSucceeded;
import actors.UserManager.RemoveFromGame;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import model.GameProtos.Game;
import utils.ConversionUtils;
import utils.DbUtils;

public class GamesManager extends AbstractActor {

	static public Props props() {
		return Props.create(GamesManager.class, () -> new GamesManager());
	}

	public GamesManager() {
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(CreateGame.class, message -> {
			String gameId = String.valueOf(new Random().nextLong());
			message.userManager.tell(new AddToGame(gameId, getSender()), getSelf());
		}).match(AddToGameSucceeded.class, message -> {
			MongoClient client = new MongoClient();
			try {
				Game game = Game.newBuilder().setId(message.getGameId()).setMaxSize(5).setMinSize(2)
						.addUserIds(message.getUserId()).build();
				DbUtils.games(client).insertOne(ConversionUtils.toDocument(game));
				message.getHttp().tell(new CreateGameSucceeded(game), getSelf());
			} catch (Throwable th) {
				getSender().tell(new RemoveFromGame(message.getGameId(), message.getHttp()), getSelf());
				message.getHttp().tell(new CreateGameFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(AddToGameFailed.class, message -> {
			message.getHttp().tell(new CreateGameFailed(message.getCause()), getSelf());
		}).matchAny(message -> {
			throw new IllegalStateException("Received unknown message of type: " + message.getClass());
		}).build();
	}

	static public class CreateGame {

		private final ActorRef userManager;

		public CreateGame(ActorRef userManager) {
			this.userManager = userManager;
		}
	}

	static public class CreateGameFailed {

		private final Throwable cause;

		public CreateGameFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class CreateGameSucceeded {

		private final Game game;

		public CreateGameSucceeded(Game game) {
			this.game = game;
		}

		public Game getGame() {
			return game;
		}
	}
}
