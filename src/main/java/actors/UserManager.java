package actors;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.google.common.base.Strings;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mongodb.MongoClient;

import akka.actor.AbstractActor;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Props;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import model.UserAlreadyInGameException;
import model.UserNotFoundException;
import model.UserProtos.User;
import scala.concurrent.Await;
import scala.concurrent.Future;
import utils.ConversionUtils;
import utils.DbUtils;

public class UserManager extends AbstractActor {

	static public Props props(String userId) {
		return Props.create(UserManager.class, () -> new UserManager(userId));
	}

	private final String userId;

	public UserManager(String userId) {
		this.userId = userId;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(GetUser.class, message -> {
			MongoClient client = new MongoClient();
			try {
				User user = getUser(client);
				getSender().tell(new GetUserSucceeded(user), getSelf());
			} catch (Throwable th) {
				getSender().tell(new GetUserFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(AddToGame.class, message -> {
			MongoClient client = new MongoClient();
			try {
				User user = getUser(client);
				if (!Strings.isNullOrEmpty(user.getGameId())) {
					ActorSelection sel = getContext().actorSelection("/user/GameManager_" + user.getGameId());
					Timeout t = new Timeout(5, TimeUnit.SECONDS);
				    AskableActorSelection asker = new AskableActorSelection(sel);
				    Future<Object> fut = asker.ask(new Identify(1), t);
				    ActorIdentity ident = (ActorIdentity)Await.result(fut, t.duration());
				    Optional<ActorRef> ref = ident.getActorRef();
				    if (ref.isPresent()) {
				    	throw new UserAlreadyInGameException(user.getUsername() + " is already in a game.");
				    }
				}
				user = user.toBuilder().setGameId(message.gameId).build();
				DbUtils.users(client).replaceOne(DbUtils.hasId(user.getId()), ConversionUtils.toDocument(user));
				getSender().tell(new AddToGameSucceeded(message.http, message.gameId, userId), getSelf());
			} catch (Throwable th) {
				getSender().tell(new AddToGameFailed(message.http, th), getSelf());
			} finally {
				client.close();
			}
		}).match(RemoveFromGame.class, message -> {
			MongoClient client = new MongoClient();
			try {
				User user = getUser(client);
				if (message.gameId.equals(user.getGameId())) {
					user = user.toBuilder().clearGameId().build();
					DbUtils.users(client).replaceOne(DbUtils.hasId(user.getId()), ConversionUtils.toDocument(user));
				}
				getSender().tell(new RemoveFromGameSucceeded(message.http), getSelf());
			} catch (Throwable th) {
				// TODO: If user was trying to join, this will lead to an inconsistency. Thus, it shouldn't fail.
				getSender().tell(new RemoveFromGameFailed(message.http, th), getSelf());
			} finally {
				client.close();
			}
		}).matchAny(message -> {
			throw new IllegalStateException("Received unknown message of type: " + message.getClass());
		}).build();
	}

	private User getUser(MongoClient client) throws UserNotFoundException, InvalidProtocolBufferException {
		Iterator<Document> cursor = DbUtils.users(client).find(DbUtils.hasId(userId)).iterator();
		if (!cursor.hasNext()) {
			throw new UserNotFoundException("User with id " + userId + " not found.");
		} else {
			Document doc = cursor.next();
			User.Builder userBuilder = User.newBuilder();
			doc.remove("_id");
			JsonFormat.parser().merge(doc.toJson(), userBuilder);
			if (cursor.hasNext()) {
				throw new IllegalStateException("More than one user with id " + userId);
			}
			return userBuilder.build();
		}
	}

	static public class GetUser {
	}

	static public class GetUserFailed extends MessageFailed {

		public GetUserFailed(Throwable cause) {
			super(cause);
		}
	}

	static public class GetUserSucceeded {

		private final User user;

		public GetUserSucceeded(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}
	}

	static public class AddToGame {

		private final String gameId;
		private final ActorRef http;
		
		public AddToGame(String gameId, ActorRef http) {
			super();
			this.gameId = gameId;
			this.http = http;
		}
	}

	static public class AddToGameFailed extends MessageFailed {

		private final ActorRef http;
		
		public AddToGameFailed(ActorRef http, Throwable cause) {
			super(cause);
			this.http = http;
		}

		public ActorRef getHttp() {
			return http;
		}
	}

	static public class AddToGameSucceeded {
		
		private final ActorRef http;
		private final String gameId;
		private final String userId;

		public AddToGameSucceeded(ActorRef http, String gameId, String userId) {
			this.http = http;
			this.gameId = gameId;
			this.userId = userId;
		}

		public ActorRef getHttp() {
			return http;
		}

		public String getGameId() {
			return gameId;
		}

		public String getUserId() {
			return userId;
		}
	}
	
	static public class RemoveFromGame {

		private final String gameId;
		private final ActorRef http;
		
		public RemoveFromGame(String gameId, ActorRef http) {
			super();
			this.gameId = gameId;
			this.http = http;
		}
	}

	static public class RemoveFromGameFailed extends MessageFailed {

		private final ActorRef http;

		public RemoveFromGameFailed(ActorRef http, Throwable cause) {
			super(cause);
			this.http = http;
		}

		public ActorRef getHttp() {
			return http;
		}
	}

	static public class RemoveFromGameSucceeded {
		
		private final ActorRef http;

		public RemoveFromGameSucceeded(ActorRef http) {
			this.http = http;
		}

		public ActorRef getHttp() {
			return http;
		}
	}
}
