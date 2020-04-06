package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class represents one token of input text.
 * 
 * @author Filip Hustić
 *
 */
public class SmartScriptToken {

	/**
	 * Value of token.
	 */
	private Element value;

	/**
	 * Type of the token.
	 */
	private SmartScriptTokenType type;

	/**
	 * Constructor which takes in token type and value of token.
	 * 
	 * @param type  type that @this token is.
	 * @param value value of @this token.
	 */
	public SmartScriptToken(SmartScriptTokenType type, Element value) {
		this.type = type;
		this.value = value;

	}

	/**
	 * 
	 * @return value of token.
	 */
	public Element getValue() {
		return value;
	}

	/**
	 * 
	 * @return type of token.
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
}
