package coloring.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class holds static methods for space state search. Provided algorithms are
 * breadth first search, depth first search and breadth first search with list
 * of visited states.
 * 
 * @author Filip Hustić
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Method is implementation of bread first search.
	 * 
	 * @param s0         initial state supplier.
	 * @param process    consumer which does action over the given state.
	 * @param succ       function which returns successors of given state.
	 * @param acceptable predicate which tests if state is acceptable.
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> opened = new LinkedList<S>();
		opened.add(s0.get());

		while (!opened.isEmpty()) {
			S head = opened.poll();

			if (!acceptable.test(head)) {
				continue;
			}

			process.accept(head);
			opened.addAll(succ.apply(head));

		}
	}

	/**
	 * Method is implementation of depth first search.
	 * 
	 * @param s0         initial state supplier.
	 * @param process    consumer which does action over the given state.
	 * @param succ       function which returns successors of given state.
	 * @param acceptable predicate which tests if state is acceptable.
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> opened = new ArrayList<S>();
		opened.add(s0.get());

		while (!opened.isEmpty()) {
			S head = opened.remove(0);

			if (!acceptable.test(head)) {
				continue;
			}

			process.accept(head);
			opened.addAll(0, succ.apply(head));
		}
	}

	/**
	 * Method is implementation of bread first search with visited states list.
	 * 
	 * @param s0         initial state supplier.
	 * @param process    consumer which does action over the given state.
	 * @param succ       function which returns successors of given state.
	 * @param acceptable predicate which tests if state is acceptable.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> opened = new LinkedList<S>();
		opened.add(s0.get());
		HashSet<S> visited = new HashSet<S>();
		visited.add(s0.get());

		while (!opened.isEmpty()) {
			S head = opened.poll();

			if (!acceptable.test(head)) {
				continue;
			}

			process.accept(head);
			// get successors from succ function and filter those who are not in visited.
			List<S> successors = succ.apply(head).stream().filter(s -> !visited.contains(s))
					.collect(Collectors.toList());

			opened.addAll(successors);
			visited.addAll(successors);
		}
	}

}
