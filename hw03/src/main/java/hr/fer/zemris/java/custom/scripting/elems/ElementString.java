package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Class which object represents value of element in parse.
 * @author Filip Hustić
 *
 */
public class ElementString extends Element{

	/**
	 * Value of element.
	 */
	private String value;

	/**
	 * Constructo that takes in Elements string value.
	 * @param value Elements string value.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Returns String representation of value of this Element.
	 */
	@Override
	public String asText() {
		return value;
	}
	
	
	
	
}
