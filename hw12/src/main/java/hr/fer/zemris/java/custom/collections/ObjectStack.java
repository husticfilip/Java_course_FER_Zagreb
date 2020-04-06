package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class represents stack with its push and pop functionality.
 * 
 * @author Filip Hustić
 *
 */
public class ObjectStack<T> {

	/**
	 * Collection used to mimic stack.(izpod haube)
	 */
	private ArrayIndexedCollection<T> collection;

	/**
	 * Default constructor. It initializes the stack.
	 */
	public ObjectStack() {
		this.collection = new ArrayIndexedCollection<T>();
	}

	/**
	 * Method checks if stack is empty.
	 * @return true if stack is empty and false otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Method returns number of elements on the stack.
	 * @return number of elements on the stack.
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * Method pushes given value on the stack.
	 * 
	 * @param value to be pushed on the stack.
	 * @throws NullPointerException if given object is null.
	 */
	public void push(T value) {
		if (value == null)
			throw new NullPointerException();

		collection.add(value);
	}

	/**
	 * Method pops peak value from the stack.It means that peak value is returned
	 * and then removed from the stack.
	 * 
	 * @return value at the top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public T pop() {
		if (collection.isEmpty())
			throw new EmptyStackException("Stack is empty.");

		T peak = collection.get(collection.size()-1);
		collection.remove(collection.size()-1);

		return peak;
	}

	/**
	 * Method returns peak value from the stack. It means that peak value is
	 * returned but kept on the stack.
	 * 
	 * @return value at the top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public T peak() {
		if (collection.isEmpty())
			throw new EmptyStackException("Stack is empty.");

		return collection.get(collection.size()-1);
	}
	
	/**
	 * Method removes all elements on the stack.
	 */
	public void clear() {
		collection.clear();
	}

}
