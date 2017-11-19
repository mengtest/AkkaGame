package model;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -7459274217227921967L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
