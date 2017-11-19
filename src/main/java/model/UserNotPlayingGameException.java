package model;

public class UserNotPlayingGameException extends Exception {

	private static final long serialVersionUID = 8574456310504184417L;

	public UserNotPlayingGameException(String message) {
		super(message);
	}
}
