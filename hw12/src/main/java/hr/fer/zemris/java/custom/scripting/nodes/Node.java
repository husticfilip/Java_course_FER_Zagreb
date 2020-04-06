package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Filip Hustić
 *
 */
public class Node {

	/**
	 * Collection of nodes which are children to @this node
	 */
	private ArrayIndexedCollection<Node> children;

	/**
	 * Adds given node to children collection of @this node. Also if children
	 * collection is uninitialized, collection is first initialized and then adds
	 * child to it.
	 * 
	 * @param child to be added in children collection.
	 * @throws NullPointerException if given child is null.
	 */
	public void addChildNode(Node child) {
		if (child == null)
			throw new NullPointerException("Given child is null");

		if (children == null) {
			children = new ArrayIndexedCollection<Node>();
		}

		children.add(child);
	}

	/**
	 * Returns a number of (direct) children.
	 * 
	 * @return
	 */
	public int numberOfChildren() {
		if (children == null)
			return 0;
		return children.size();
	}

	/**
	 * Returns node at given index.
	 * 
	 * @param index of node to be returned
	 * @return node at given index.
	 * @throws IndexOutOfBoundsException if there is no node on given index.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}
	
	/**
	 * Method calls visitors visit method on @this object.
	 * @param visitor
	 */
	public void accept(INodeVisitor visitor) {
		throw new UnsupportedOperationException("Accept is not implement for Node class.");
	}

}
