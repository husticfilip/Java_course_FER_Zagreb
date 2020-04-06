package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Class which object represents double value in lexer analysis.
 * @author Filip Hustić
 *
 */
public class ElementConstantDouble extends Element{
	
	/**
	 * Value of double.
	 */
	private double value;

	/**
	 * Constructor that sets value of Element.
	 * @param value double value of this element.
	 */
	public ElementConstantDouble(double value) {
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
