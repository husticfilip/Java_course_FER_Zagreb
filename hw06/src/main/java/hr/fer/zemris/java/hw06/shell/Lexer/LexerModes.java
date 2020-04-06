package hr.fer.zemris.java.hw06.shell.Lexer;

/**
 * In which modes can ShellLexer work.
 * 
 * @author Filip Hustić
 *
 */
public enum LexerModes {

	/**
	 * Next token is path.
	 */
	PATH_MODE,
	/**
	 * Next token is name of argumenr.
	 */
	NAME_MODE

}
