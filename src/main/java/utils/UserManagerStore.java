package utils;

import static akka.pattern.PatternsCS.ask;

import java.util.HashMap;

import actors.UsersManager.GetUserManager;
import actors.UsersManager.GetUserManagerFailed;
import actors.UsersManager.GetUserManagerSucceeded;
import akka.actor.ActorRef;

public class UserManagerStore {

	private final HashMap<String, ActorRef> userManagersMap = new HashMap<>();
	private final ActorRef usersManager;

	public UserManagerStore(ActorRef usersManager) {
		this.usersManager = usersManager;
	}

	public void checkIfExists(String userId, long timeout) throws Throwable {
		if (!userManagersMap.containsKey(userId)) {
			Object message = ask(usersManager, new GetUserManager(userId), timeout).toCompletableFuture().get();
			if (message instanceof GetUserManagerSucceeded) {
				userManagersMap.put(userId, ((GetUserManagerSucceeded) message).getUserManager());
			} else if (message instanceof GetUserManagerFailed) {
				throw ((GetUserManagerFailed) message).getCause();
			} else {
				throw new IllegalStateException("Unexpected message of type: " + message.getClass());
			}
		}
	}

	public ActorRef get(String userId) {
		return userManagersMap.get(userId);
	}
}
