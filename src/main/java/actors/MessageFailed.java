package actors;

public class MessageFailed {

	private final Throwable cause;

	public MessageFailed(Throwable cause) {
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}
