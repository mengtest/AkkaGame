package model;

public class UserAlreadyInGameException extends Exception {

	private static final long serialVersionUID = -9034261930207549612L;

	public UserAlreadyInGameException(String message) {
		super(message);
	}
}
