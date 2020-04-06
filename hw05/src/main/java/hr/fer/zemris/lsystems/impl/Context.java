package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class which holds stack of turtle states.
 * 
 * @author Filip Hustić
 *
 */
public class Context {

	/**
	 * Stack which stores states of turtle. Current active state is on the top of
	 * the stack.
	 */
	private ObjectStack<TurtleState> objectStack;

	/**
	 * Default constructor. It initializes stack which will hold turtle states.
	 */
	public Context() {
		super();
		objectStack = new ObjectStack<TurtleState>();
	}

	/**
	 * Method returns active state of the turtle.
	 * 
	 * @return current state of the turtle.
	 */
	public TurtleState getCurrentState() {
		return objectStack.peak();
	}

	/**
	 * Method sets up new active state of the turtle.
	 * 
	 * @param state new TurtleState.
	 */
	public void pushState(TurtleState state) {
		objectStack.push(state);

	}

	/**
	 * Method removes active state of the turtle.
	 */
	public void popState() {
		objectStack.pop();
	}

}
