package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * 
 * @author Filip Hustić
 *
 */
public class EchoNode extends Node {

	/**
	 * Elements of EchoNode which can be variables,strings, functions, operators and
	 * numbers.
	 */
	private Element[] elements;

	/**
	 * Constructor which takes in array of elements of this EchoNode.
	 * 
	 * @param elements array of elements.
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * @return array of Elements of this EchoNode.
	 */
	public Element[] getElements() {
		return elements;
	}

}
