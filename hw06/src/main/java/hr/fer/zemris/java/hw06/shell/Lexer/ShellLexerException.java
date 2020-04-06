package hr.fer.zemris.java.hw06.shell.Lexer;

/**
 * Exception thrown when there was problem in parsing query.
 * 
 * @author Filip Hustić
 *
 */
public class ShellLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShellLexerException(String message) {
		super(message);
	}

	public ShellLexerException(Throwable cause) {
		super(cause);
	}

	public ShellLexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
