package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which object represents variable in lexer analysis.
 * @author Filip Hustić
 *
 */
public class ElementVariable extends Element{

	/**
	 * Name of variable.
	 */
	private String name;
	
	
	/**
	 * Constructor which takes in name of the variable.
	 * @param name of the variable.
	 */
	public ElementVariable(String name) {
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
