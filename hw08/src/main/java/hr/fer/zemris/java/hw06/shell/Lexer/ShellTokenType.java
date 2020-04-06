package hr.fer.zemris.java.hw06.shell.Lexer;

/**
 * Type of ShellToken.
 * 
 * @author Filip Hustić
 *
 */
public enum ShellTokenType {
	/**
	 * Token holds path.
	 */
	PATH,
	/**
	 * Token holds argument name.
	 */
	NAME,

	/**
	 * Token holds regex pattern.
	 */
	PATTTERN
}
