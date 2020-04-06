package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents single element that lexer produces.
 * @author Filip Hustić
 *
 */
public abstract class Element {
	
	/**
	 * Returns property of the element in form of string.
	 * @return
	 */
	public abstract String asText();

}
