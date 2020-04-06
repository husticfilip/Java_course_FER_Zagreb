package searching.algorithms;

/**
 * Class represents transition from one node to its successor. When generating
 * successor that successor has state and cost of getting from current node to
 * that successor.
 * 
 * @author Filip Hustić
 *
 * @param <S>
 */
public class Transition<S> {

	/**
	 * State of successor node.
	 */
	private S state;
	/**
	 * Cost of getting to successor node.
	 */
	private double cost;

	/**
	 * Constructor which takes in successor's state and cost of getting to that
	 * successor.
	 * 
	 * @param state of successor.
	 * @param cost  of getting to successor.
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	/**
	 * 
	 * @return state of successor.
	 */
	public S getState() {
		return state;
	}

	/**
	 * 
	 * @return cost of getting to successor state.
	 */
	public double getCost() {
		return cost;
	}

}
