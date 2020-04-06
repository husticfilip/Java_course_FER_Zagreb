package hr.fer.zemris.java.hw05.db;

/**
 * Interface which represents comparison operator which operates on two strings.
 * 
 * @author Filip Hustić
 *
 */
public interface IComparisonOperator {

	/**
	 * If comparison satisfies some conditions about two strings method returns
	 * true, else false.
	 * 
	 * @param value1
	 * @param value2
	 * @return If comparison satisfies some conditions about two strings method
	 *         returns true, else false.
	 */
	public boolean satisfied(String value1, String value2);
}
