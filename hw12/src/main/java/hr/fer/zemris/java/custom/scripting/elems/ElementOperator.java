package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which object represents operator in lexer analysis.
 * @author Filip Hustić
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * Symbol of operator.
	 */
	private String symbol;

	/**
	 * Constructor that takes in symbol of operator.
	 * @param symbol of operator.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}


	/**
	 * Returns String representation of value of this Element.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	

}
