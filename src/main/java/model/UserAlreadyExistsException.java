package model;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1112794301594674861L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
