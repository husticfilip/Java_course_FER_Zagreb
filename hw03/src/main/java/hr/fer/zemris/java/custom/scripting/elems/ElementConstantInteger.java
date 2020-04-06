package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which object represents one integer value in lexer analysis.
 * @author Filip Hustić
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Value of integer.
	 */
	private int value;

	
	/**
	 * Constructor that sets value of Element.
	 * @param value int value of this element.
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}


	/**
	 * Returns String representation of value of this Element.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
