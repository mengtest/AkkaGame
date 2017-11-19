package model;

public class GameIsFullException extends Exception {

	private static final long serialVersionUID = -2918033566193641085L;

	public GameIsFullException(String message) {
		super(message);
	}
}
