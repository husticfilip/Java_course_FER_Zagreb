package hr.fer.zemris.java.hw03.prob1;

/**
 * Class represents one token of input text.
 * @author Filip Hustić
 *
 */
public class Token {

	/**
	 * Value of token.
	 */
	private Object value;

	/**
	 * Type of the token.
	 */
	private TokenType type;

	/**
	 * Constructor which takes in token type and value of token.
	 * 
	 * @param type type that @this token is.
	 * @param value value of @this token.
	 */
	public Token(TokenType type, Object value) {
		this.type=type;
		this.value=value;

	}

	/**
	 * 
	 * @return value of token.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 
	 * @return type of token.
	 */
	public TokenType getType() {
		return type;
	}
}
