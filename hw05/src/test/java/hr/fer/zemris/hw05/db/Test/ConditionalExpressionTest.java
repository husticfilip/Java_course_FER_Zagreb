package hr.fer.zemris.hw05.db.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class ConditionalExpressionTest {

	@Test
	public void testSatisifes() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Boš*",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("0000000001", "Boško", "Bo", 5);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testDoesNotSatisife() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Boš*",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("0000000001", "Bosko", "Bo", 5);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		
		assertFalse(recordSatisfies);
	}

}
