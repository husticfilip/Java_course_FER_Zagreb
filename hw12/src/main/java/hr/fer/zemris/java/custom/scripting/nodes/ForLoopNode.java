package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Filip Hustić
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable of loop.
	 */
	private ElementVariable variable;
	/**
	 * First element of loop.
	 */
	private Element startExpression;
	/**
	 * Last element of loop.
	 */
	private Element endExpression;
	/**
	 * Middle element od loop. Can be null if there is not one.
	 */
	private Element stepExpression;

	/**
	 * Constructro which takes in elements of for loop.
	 * 
	 * @param variable        of loop.
	 * @param startExpression of loop.
	 * @param endExpression   of loop.
	 * @param stepExpression  of loop.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
	
	/**
	 * 
	 * @return variable.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * 
	 * @return startExpression.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * 
	 * @return endExpression.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * 
	 * @return stepExpression.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
