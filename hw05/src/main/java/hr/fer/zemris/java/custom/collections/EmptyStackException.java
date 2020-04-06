package hr.fer.zemris.java.custom.collections;

/**
 * Class to handle exceptions thrown by because the stack was empty.
 * @author Filip Hustić
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {

	}

	/**
	 * 
	 * @param message to be send into parents constructor.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause to be send into parents constructor.
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message to be send into parents constructor.
	 * @param cause   to be send into parents constructor.
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
