package actors;

import java.util.HashMap;
import java.util.Random;

import com.mongodb.MongoClient;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import model.UserAlreadyExistsException;
import model.UserNotFoundException;
import model.UserProtos.User;
import utils.ConversionUtils;
import utils.DbUtils;

public class UsersManager extends AbstractActor {

	static public Props props(ActorSystem system) {
		return Props.create(UsersManager.class, () -> new UsersManager(system));
	}
	
	private final HashMap<String, ActorRef> userManagersMap = new HashMap<> ();
	private final ActorSystem system;
	
	public UsersManager(ActorSystem system) {
		this.system = system;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(CreateUserByUsername.class, message -> {
			MongoClient client = new MongoClient();
			try {
				long count = DbUtils.users(client).count(DbUtils.has("username", message.username));
				if (count > 0) {
					throw new UserAlreadyExistsException(message.username + " already exists.");
				} else {
					User user = User.newBuilder().setId(String.valueOf(new Random().nextLong()))
							.setUsername(message.username).build();
					DbUtils.users(client).insertOne(ConversionUtils.toDocument(user));
					addManager(user.getId());
					getSender().tell(new CreateUserSucceeded(user), getSelf());
				}
			} catch (Throwable th) {
				getSender().tell(new CreateUserFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(GetUserManager.class, message -> {
			if (!userManagersMap.containsKey(message.userId)) {
				MongoClient client = new MongoClient();
				try {
					long count = DbUtils.users(client).count(DbUtils.hasId(message.userId));
					if (count == 0) {
						throw new UserNotFoundException("User with id " + message.userId + " not found.");
					} else {
						addManager(message.userId);
					}
				} catch (Throwable th) {
					getSender().tell(new GetUserManagerFailed(th), getSelf());
				} finally {
					client.close();
				}
			}
			getSender().tell(new GetUserManagerSucceeded(userManagersMap.get(message.userId)), getSelf());
		}).matchAny(message -> {
			throw new IllegalStateException("Received unknown message of type: " + message.getClass());
		}).build();
	}
	
	public void addManager(String userId) {
		ActorRef userManager = system.actorOf(UserManager.props(userId), "UserManager_" + userId);
		userManagersMap.put(userId, userManager);
	}
	
	static public class CreateUserByUsername {

		private final String username;

		public CreateUserByUsername(String username) {
			this.username = username;
		}
	}

	static public class CreateUserFailed {

		private final Throwable cause;

		public CreateUserFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class CreateUserSucceeded {

		private final User user;

		public CreateUserSucceeded(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}
	}
	
	static public class GetUserManager {
		
		private final String userId;

		public GetUserManager(String userId) {
			super();
			this.userId = userId;
		}
	}

	static public class GetUserManagerFailed {

		private final Throwable cause;

		public GetUserManagerFailed(Throwable cause) {
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}
	}

	static public class GetUserManagerSucceeded {

		private final ActorRef userManager;

		public GetUserManagerSucceeded(ActorRef userManager) {
			this.userManager = userManager;
		}

		public ActorRef getUserManager() {
			return userManager;
		}
	}
}
