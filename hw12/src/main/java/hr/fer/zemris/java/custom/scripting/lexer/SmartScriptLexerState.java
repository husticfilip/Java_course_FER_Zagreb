package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum represents state in which lexer can be.
 * 
 * @author Filip Hustić
 *
 */
public enum SmartScriptLexerState {

	/**
	 * State for which rules inside of tags apply.
	 */
	TAG_STATE,

	/**
	 * State for which rules outside of tags apply.
	 */
	OUTSIDE_OF_TAGS_STATE

}
