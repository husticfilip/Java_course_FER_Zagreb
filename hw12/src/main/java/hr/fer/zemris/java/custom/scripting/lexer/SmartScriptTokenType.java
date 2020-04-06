package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum represents types that token produced by lexer can be.
 * 
 * @author Filip Hustić
 *
 */
public enum SmartScriptTokenType {

	/**
	 * End of file.
	 */
	EOF,

	/**
	 * Variable.
	 */
	VARIABLE,

	/**
	 * Function.
	 */
	FUNCTION,

	/**
	 * Number
	 */
	NUMBER,

	/**
	 * String.
	 */
	STRING,

	/**
	 * Operator.
	 */
	OPERATOR,

	/**
	 * Start of tag {$.
	 */
	START_OF_TAG,

	/**
	 * End of tag $}.
	 */
	END_OF_TAG,

	/**
	 * End tag {$END$}.
	 */
	END_TAG
}
