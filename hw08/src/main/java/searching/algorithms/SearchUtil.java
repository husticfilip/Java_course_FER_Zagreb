package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class holds methods for state space searching. Provided methods are Breadth
 * first search(bfs) and Breadth first search with list of visited states(bfsv).
 * 
 * @author Filip Hustić
 *
 */
public class SearchUtil {

	/**
	 * Method is implementation of breadth first search.
	 * 
	 * @param s0   supplier of initial state.
	 * @param succ successor function.
	 * @param goal predicate which tests if provided state is goal state.
	 * @return list goal state if there is one, null otherwise.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> opened = new LinkedList<>();
		opened.add(new Node(null, s0.get(), 0));

		while (!opened.isEmpty()) {
			Node head = opened.remove(0);

			if (goal.test((S) head.getState())) {
				return head;
			}

			List<Transition<S>> successors = succ.apply((S) head.getState());

			for (Transition successor : successors) {
				opened.add(new Node(head, successor.getState(), successor.getCost() + head.getCost()));
			}
		}
		return null;
	}

	/**
	 * Method is implementation of breadth first search with list of visited states.
	 * 
	 * @param s0   supplier of initial state.
	 * @param succ successor function.
	 * @param goal predicate which tests if provided state is goal state.
	 * @return list goal state if there is one, null otherwise.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> opened = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		opened.add(new Node(null, s0.get(), 0));
		visited.add(s0.get());

		while (!opened.isEmpty()) {
			Node head = opened.remove(0);

			if (goal.test((S) head.getState())) {
				return head;
			}

			List<Transition<S>> successors = succ.apply((S) head.getState()).stream()
					.filter((s) -> !visited.contains(s.getState())).collect(Collectors.toList());

			for (Transition successor : successors) {
				opened.add(new Node(head, successor.getState(), successor.getCost() + head.getCost()));
				visited.add((S) successor.getState());
			}
		}
		return null;
	}

}
