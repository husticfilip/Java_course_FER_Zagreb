package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Enum of lexer work modes.
 * 
 * @author Filip Hustić
 *
 */
public enum QueryLexerModes {

	/**
	 * Lexer looks up for and operator.
	 */
	AND_MODE,
	/**
	 * Lexer looks up for string literal specific for LIKE operator.
	 */
	LIKE_LITERAL_MODE,
	/**
	 * Lexer looks up for string literal specific foe every other operator except
	 * LIKE.
	 */
	OPERATOR_LITERAL_MODE,
	/**
	 * Lexer looks up for next attribute and operator.
	 */
	ATRIBUT_MODE

}
