package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which object represent element of function in lexer analysis.
 * @author Filip Hustić
 *
 */
public class ElementFunction extends Element {
	
	/**
	 * Value of element.
	 */
	private String name;

	
	/**
	 * Constructor which takes in name of the function.
	 * @param name name of the function.
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}


	/**
	 * Returns String representation of value of this Element.
	 */
	@Override
	public String asText() {
		return name;
	}

	
}
