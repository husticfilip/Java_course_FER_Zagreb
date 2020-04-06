package hr.fer.zemris.java.hw06.shell;



/**
 * Exception thrown when error occurs while woriking with shell and its commands.
 * @author Filip Hustić
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1L;

	public ShellIOException(String message) {
		super(message);
	}

	public ShellIOException(Throwable cause) {
		super(cause);
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
