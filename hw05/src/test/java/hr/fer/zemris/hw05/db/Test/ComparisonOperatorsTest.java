package hr.fer.zemris.hw05.db.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;

		assertTrue(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void testLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;

		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;

		assertTrue(oper.satisfied("Jasna","Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;

		assertTrue(oper.satisfied("Jasna","Ana"));
		assertTrue(oper.satisfied("Ana","Ana"));
		}

	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;

		assertTrue(oper.satisfied("Ana","Ana"));
		}

	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;

		assertTrue(oper.satisfied("Ana","Jasna"));
		}
	
	@Test
	public void testLikeWildCardInBeggining() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Ana","*"));
		}
	
	@Test
	public void testLikeWildCardInBegginign2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Ana","*a"));
		}
	
	@Test
	public void testLikeWildCardInMiddle() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Anastazija","Ana*zija"));
		}

	@Test
	public void testLikeWildCardOnEnd() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Anastazija", "Anas*"));
	}
	
	@Test
	public void testNotLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Ana","Ane"));
		}
	
	@Test
	public void testNotLikeWithWildCardInBeggining() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Anae","*as"));
		}
	
	@Test
	public void testNotLikeWithWildCardInMiddle() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Anastazija","Ana*zie"));
		}
	
	@Test
	public void testPatternLongerThanString() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Ana","Anaas*"));
		}
	
	@Test
	public void testWildCardJustBehindEnd() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertTrue(oper.satisfied("Ana","Ana*"));
		}
	
	@Test
	public void testStringLongerThanPattern() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Anastazija","Ana"));
		}
	@Test
	public void testPatternLongerThanString2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Ana","Anastazija"));
		}
	@Test
	public void testHomeworkEq() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));

	
	}
}
