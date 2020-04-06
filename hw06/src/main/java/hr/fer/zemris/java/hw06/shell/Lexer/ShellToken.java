package hr.fer.zemris.java.hw06.shell.Lexer;

/**
 * Class represents token produced by ShellLexer.
 * 
 * @author Filip Hustić
 *
 */
public class ShellToken {

	/**
	 * Value of token.
	 */
	private String value;
	/**
	 * Type of token.
	 */
	private ShellTokenType type;

	/*
	 * @param value of token.
	 * 
	 * @param type of token.
	 */
	public ShellToken(String value, ShellTokenType type) {
		super();
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public ShellTokenType getType() {
		return type;
	}

}
