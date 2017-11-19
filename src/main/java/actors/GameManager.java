package actors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mongodb.MongoClient;

import actors.UserManager.AddToGame;
import actors.UserManager.AddToGameFailed;
import actors.UserManager.AddToGameSucceeded;
import actors.UserManager.RemoveFromGame;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import model.GameIsFullException;
import model.GameNotFoundException;
import model.UserNotPlayingGameException;
import model.GameProtos.Game;
import utils.ConversionUtils;
import utils.DbUtils;

public class GameManager extends AbstractActor {

	static public Props props(String gameId) {
		return Props.create(GameManager.class, () -> new GameManager(gameId));
	}

	private final String gameId;

	public GameManager(String gameId) {
		this.gameId = gameId;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(GetGame.class, message -> {
			MongoClient client = new MongoClient();
			try {
				Game game = getGame(client);
				getSender().tell(new GetGameSucceeded(game), getSelf());
			} catch (Throwable th) {
				getSender().tell(new GetGameFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(JoinGame.class, message -> {
			MongoClient client = new MongoClient();
			try {
				Game game = getGame(client);
				if (game.getUserIdsCount() + 1 > game.getMaxSize()) {
					throw new GameIsFullException(
							"The game has " + game.getUserIdsCount() + " out of " + game.getMaxSize() + " players.");
				}
				message.userManager.tell(new AddToGame(gameId, getSender()), getSelf());
			} catch (Throwable th) {
				getSender().tell(new JoinGameFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(AddToGameSucceeded.class, message -> {
			MongoClient client = new MongoClient();
			try {
				Game game = getGame(client);
				if (game.getUserIdsCount() + 1 > game.getMaxSize()) {
					throw new GameIsFullException(
							"The game has " + game.getUserIdsCount() + " out of " + game.getMaxSize() + " players.");
				}
				game = game.toBuilder().addUserIds(message.getUserId()).build();
				DbUtils.games(client).replaceOne(DbUtils.hasId(gameId), ConversionUtils.toDocument(game));
				message.getHttp().tell(new JoinGameSucceeded(), getSelf());
			} catch (Throwable th) {
				getSender().tell(new RemoveFromGame(gameId, message.getHttp()), ActorRef.noSender());
				message.getHttp().tell(new JoinGameFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(AddToGameFailed.class, message -> {
			message.getHttp().tell(new JoinGameFailed(message.getCause()), getSelf());
		}).match(RemoveUserFromGame.class, message -> {
			MongoClient client = new MongoClient();
			try {
				Game game = getGame(client);
				List<String> userIds = new ArrayList<>(game.getUserIdsList());
				if (!userIds.contains(message.userId)) {
					throw new UserNotPlayingGameException("User with id " + message.userId +
							" is not playing game with id " + gameId);
				}
				userIds.remove(message.userId);
				game = game.toBuilder().clearUserIds().addAllUserIds(userIds).build();
				DbUtils.games(client).replaceOne(DbUtils.hasId(gameId), ConversionUtils.toDocument(game));
				message.userManager.tell(new RemoveFromGame(gameId, getSender()), ActorRef.noSender());
				getSender().tell(new RemoveUserFromGameSucceeded(), getSelf());
			} catch (Throwable th) {
				getSender().tell(new RemoveUserFromGameFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).matchAny(message -> {
			throw new IllegalStateException("Received unknown message of type: " + message.getClass());
		}).build();
	}

	private Game getGame(MongoClient client) throws InvalidProtocolBufferException, GameNotFoundException {
		Iterator<Document> cursor = DbUtils.games(client).find(DbUtils.hasId(gameId)).iterator();
		if (!cursor.hasNext()) {
			throw new GameNotFoundException("Game with id " + gameId + " not found.");
		} else {
			Document doc = cursor.next();
			Game.Builder gameBuilder = Game.newBuilder();
			doc.remove("_id");
			JsonFormat.parser().merge(doc.toJson(), gameBuilder);
			if (cursor.hasNext()) {
				throw new IllegalStateException("More than one game with id " + gameId);
			}
			return gameBuilder.build();
		}
	}

	static public class GetGame {
	}

	static public class GetGameFailed {

		private final Throwable cause;

		public GetGameFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class GetGameSucceeded {

		private final Game game;

		public GetGameSucceeded(Game game) {
			this.game = game;
		}

		public Game getGame() {
			return game;
		}
	}

	static public class JoinGame {

		private final ActorRef userManager;

		public JoinGame(ActorRef userManager) {
			this.userManager = userManager;
		}
	}

	static public class JoinGameFailed {

		private final Throwable cause;

		public JoinGameFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class JoinGameSucceeded {
	}
	
	static public class RemoveUserFromGame {

		private String userId;
		private final ActorRef userManager;

		public RemoveUserFromGame(ActorRef userManager, String userId) {
			this.userManager = userManager;
			this.userId = userId;
		}
	}

	static public class RemoveUserFromGameFailed {

		private final Throwable cause;

		public RemoveUserFromGameFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class RemoveUserFromGameSucceeded {
	}
}
