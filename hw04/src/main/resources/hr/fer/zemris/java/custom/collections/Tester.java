package hr.fer.zemris.java.custom.collections;

/**
 * Interface class which has one method test.
 * @author Filip Hustić
 *
 */
public interface Tester {

	/**
	 * Method tests if given object satisfies
	 * implemented conditions.
	 * @param obj object which is being tested,
	 * @return true if object satisfies conditions, false
	 * 		   otherwise.
	 */
	boolean test(Object obj);
}
