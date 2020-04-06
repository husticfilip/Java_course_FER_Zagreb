package hr.fer.zemris.java.hw05.db;


/**
 * Class that holds ComparisonOperators which implement IComparisonOperator.
 * @author Filip Hustić
 *
 */
public class ComparisonOperators {

	/**
	 * Operator which returns true if first given string has less value than second given string value.
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0 ? true : false;
	/**
	 * Operator which returns true if first given string has less or equal value than second given string value.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0 ? true : false;
	/**
	 * Operator which returns true if first given string has grater value than second given string value.
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0 ? true : false;
	/**
	 * Operator which returns true if first given string has grater or equal value than second given string value.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0 ? true : false;
	/**
	 * Operator which returns true if first given string has equal value t0 second given string value.
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0 ? true : false;
	/**
	 * Operator which returns true if first given string has value not equal to second given string value.
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0 ? true : false;
	/**
	 * Operator which returns true if first given string has given pattern(second argument) in it.
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> testLike(s1, s2);

	/**
	 * Helper method which true if first given string has given pattern(second argument) in it.
	 * @param toCheck string to be check if given pattern is in it.
	 * @param pattern which is being tested.
	 * @return true if given pattern is in string, false otherwise.
	 */
	private static boolean testLike(String toCheck, String pattern) {
		char[] patternsChars = pattern.toCharArray();
		char[] toCheckChars = toCheck.toCharArray();

		int patternIndex = -1;
		for (int checkIndex = 0; checkIndex < toCheckChars.length; ++checkIndex) {
			patternIndex++;
			if (patternIndex == patternsChars.length)
				return false;

			if (patternsChars[patternIndex] == '*') {
				if (patternIndex == patternsChars.length - 1) {
					return true;
				} else {
					checkIndex = skipToCheckToChar(checkIndex, toCheckChars, patternsChars[++patternIndex]);
					if (checkIndex == toCheckChars.length)
						return false;
				}
			} else if (patternsChars[patternIndex] != toCheckChars[checkIndex]) {
				return false;
			}
		}

		//eq Ana , Ana*
		if(patternIndex==patternsChars.length-2 && patternsChars[patternsChars.length-1]=='*')
			return true;
		//not all pattern char were tested, pattern is longer than orginal string.
		else if (patternIndex < patternsChars.length -1 )
			return false;
		else
			return true;
	}

	/**
	 * Helper method which skips string values when wildCard occurs. It
	 * skips to the value that is equal to the value after wildCard in patters.
	 * @param toCheckIndex current index that points to char that is currently tested in string.
	 * @param toCheckChars string separated into char array.
	 * @param nextChar next char to find in toCheckChars.
	 * @return index of found nextChar.
	 */
	private static int skipToCheckToChar(int toCheckIndex, char[] toCheckChars, char nextChar) {

		while (toCheckIndex < toCheckChars.length && toCheckChars[toCheckIndex] != nextChar)
			++toCheckIndex;

		return toCheckIndex;
	}
}
