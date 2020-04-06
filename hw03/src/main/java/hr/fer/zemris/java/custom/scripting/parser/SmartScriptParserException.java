package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class which instance is thrown when some exception
 * occurs during parsing.
 * @author Filip Hustić
 *
 */
public class SmartScriptParserException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
