package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception thrown when there is problem with lexer operations.
 * 
 * @author Filip Hustić
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexerException(String message) {
		super(message);
	}

	public LexerException(Throwable cause) {
		super(cause);
	}

	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
