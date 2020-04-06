package searching.algorithms;

/**
 * Class represents node in search tree. Each node has its state, cost of
 * getting to this node from start node and reference to the parent node.
 * 
 * @author Filip Hustić
 *
 * @param <S>
 */
public class Node<S> {

	/**
	 * Parent node.
	 */
	private Node<S> parent;
	/**
	 * State of the node.
	 */
	private S state;
	/**
	 * Cost of getting to this node from start node.
	 */
	private double cost;

	/**
	 * Constructor which takes in parent node, state of this node and cost of
	 * getting to this node from start node.
	 * 
	 * @param parent node.
	 * @param state  of this node.
	 * @param cost   of getting to this node from start node.
	 */
	public Node(Node<S> parent, S state, double cost) {
		super();
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * 
	 * @return parent node.
	 */
	public Node<S> getParent() {
		return parent;
	}

	/*
	 * @return state of this node.
	 */
	public S getState() {
		return state;
	}

	/**
	 * 
	 * @return cost of getting to this node from parent node.
	 */
	public double getCost() {
		return cost;
	}

}
