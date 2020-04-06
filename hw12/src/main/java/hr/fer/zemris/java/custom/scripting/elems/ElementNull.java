package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Hellper Class which represents elements of lexer analyisis
 * that have value null, such as EOF, END_OF_TAG AND END_TAG.
 * @author Filip Hustić
 *
 */
public class ElementNull extends Element{

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return null;
	}

}
