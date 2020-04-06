package hr.fer.zemris.java.hw06.shell.nameFilter;

/**
 * Exception if there was exception in NameBuildParser.
 * 
 * @author Filip Hustić
 *
 */
public class NameParserException extends RuntimeException {

	/**
	 * SerialVerisonUID
	 */
	private static final long serialVersionUID = 1L;

	public NameParserException() {
		super();
	}

	public NameParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NameParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameParserException(String message) {
		super(message);
	}

	public NameParserException(Throwable cause) {
		super(cause);
	}

}
