package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Enum which represents type of token.
 * 
 * @author Filip Hustić
 *
 */
public enum QueryTokenType {
	/**
	 * Token represents and operator.
	 */
	AND,
	/**
	 * Token like and operator.
	 */
	LIKE,
	/**
	 * Token represents and >.
	 */
	GREATER,
	/**
	 * Token represents and >=.
	 */
	GREATER_OR_EQUAL,
	/**
	 * Token represents and <.
	 */
	LESS,
	/**
	 * Token represents and <=.
	 */
	LESS_OR_EQUAL,
	/**
	 * Token represents and =.
	 */
	EQUAL,
	/**
	 * Token represents and !=.
	 */
	NOT_EQUAL,
	/**
	 * Token represents and string literal.
	 */
	LITERAL
}
