package hr.fer.zemris.hw05.db.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.Lexer.QueryParserException;

public class QueryParserTest {
	
	@Test
	public void testParserQuery() {
		QueryParser parser = new QueryParser("        jmbag=\"0000000003\"");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0000000003", parser.getQueriedJMBAG());
	}
	
	@Test
	public void testParserQuery2() {
		QueryParser parser = new QueryParser("lastName = \"Blažić\"");
		List<ConditionalExpression> expressions = parser.getQuery();
		
		assertEquals(FieldValueGetters.LAST_NAME, expressions.get(0).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, expressions.get(0).getComparisonOperator());
		assertEquals("Blažić", expressions.get(0).getStringLiteral());
		assertFalse(parser.isDirectQuery());
	}
	
	@Test
	public void testParserQuery3() {
		QueryParser parser = new QueryParser("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		List<ConditionalExpression> expressions = parser.getQuery();
		
		assertEquals(FieldValueGetters.FIRST_NAME, expressions.get(0).getFieldGetter());
		assertEquals(ComparisonOperators.GREATER, expressions.get(0).getComparisonOperator());
		assertEquals("A", expressions.get(0).getStringLiteral());

		assertEquals(FieldValueGetters.FIRST_NAME, expressions.get(1).getFieldGetter());
		assertEquals(ComparisonOperators.LESS, expressions.get(1).getComparisonOperator());
		assertEquals("C", expressions.get(1).getStringLiteral());
		
		assertEquals(FieldValueGetters.LAST_NAME, expressions.get(2).getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, expressions.get(2).getComparisonOperator());
		assertEquals("B*ć", expressions.get(2).getStringLiteral());
		
		assertEquals(FieldValueGetters.JMBAG, expressions.get(3).getFieldGetter());
		assertEquals(ComparisonOperators.GREATER, expressions.get(3).getComparisonOperator());
		assertEquals("0000000002", expressions.get(3).getStringLiteral());
		
	}
	
	@Test
	public void testNotDirectQuery() {
		QueryParser parser = new QueryParser("        lastName=\"0000000003\"");

		assertThrows(QueryParserException.class, ()->{
			parser.getQueriedJMBAG();
		});
		assertFalse(parser.isDirectQuery());
	}
	
	@Test
	public void homeworkExamples() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue( qp1.isDirectQuery());
		assertEquals("0123456789",qp1.getQueriedJMBAG()); 
		assertEquals(1,qp1.getQuery().size()); 
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertThrows(QueryParserException.class, ()->{
			qp2.getQueriedJMBAG();
		});
		assertEquals(2,qp2.getQuery().size()); 
	}


}
