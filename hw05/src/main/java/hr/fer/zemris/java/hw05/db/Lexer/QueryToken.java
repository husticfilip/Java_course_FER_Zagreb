package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Class which represents query token.
 * 
 * @author Filip Hustić
 *
 */
public class QueryToken {
	/**
	 * Value of token.
	 */
	public String value;
	/**
	 * Type of token.
	 */
	public QueryTokenType type;

	/**
	 * Constructor which takes in value and type of token.
	 * 
	 * @param value
	 * @param type
	 */
	public QueryToken(String value, QueryTokenType type) {
		super();
		this.value = value;
		this.type = type;
	}

}
