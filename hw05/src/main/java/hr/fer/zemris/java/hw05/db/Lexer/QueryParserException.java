package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Exception thrown when there was problem in parsing query.
 * 
 * @author Filip Hustić
 *
 */
public class QueryParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryParserException(String message) {
		super(message);
	}

	public QueryParserException(Throwable cause) {
		super(cause);
	}

	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
