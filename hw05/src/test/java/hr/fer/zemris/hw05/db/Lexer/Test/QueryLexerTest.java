package hr.fer.zemris.hw05.db.Lexer.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.Lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.Lexer.QueryLexerModes;
import hr.fer.zemris.java.hw05.db.Lexer.QueryParserException;
import hr.fer.zemris.java.hw05.db.Lexer.QueryToken;
import hr.fer.zemris.java.hw05.db.Lexer.QueryTokenType;

public class QueryLexerTest {

	@Test
	public void testNameLess() {
		QueryLexer lexer = new QueryLexer("firstName<\"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LESS, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testNameLessEquals() {
		QueryLexer lexer = new QueryLexer("firstName<=\"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LESS_OR_EQUAL, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testGreater() {
		QueryLexer lexer = new QueryLexer("  firstName  >  \"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.GREATER, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testGreaterOrEqual() {
		QueryLexer lexer = new QueryLexer("  firstName  >=  \"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.GREATER_OR_EQUAL, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testEqual() {
		QueryLexer lexer = new QueryLexer("  firstName =\"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.EQUAL, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testNotEqual() {
		QueryLexer lexer = new QueryLexer("  firstName !=\"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.NOT_EQUAL, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testLIKE() {
		QueryLexer lexer = new QueryLexer("  firstName LIKE \"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LIKE, token.type);
		assertEquals("firstName", token.value);
	}

	@Test
	public void testJmbag() {
		QueryLexer lexer = new QueryLexer("  jmbag !=\"Bosko\"");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.NOT_EQUAL, token.type);
		assertEquals("jmbag", token.value);
	}

	@Test
	public void testLastName() {
		QueryLexer lexer = new QueryLexer("  lastName!=\"Bosko\"    ");

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.NOT_EQUAL, token.type);
		assertEquals("lastName", token.value);
	}

	@Test
	public void testWrongName() {
		QueryLexer lexer = new QueryLexer("  lastNam!=\"Bosko\"    ");

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testWrongOperator() {
		QueryLexer lexer = new QueryLexer("  lastName!\"Bosko\"    ");

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testWrongOperator2() {
		QueryLexer lexer = new QueryLexer("  lastName?\"Bosko\"    ");

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testWrongOperator3() {
		QueryLexer lexer = new QueryLexer("  lastName Like \"Bosko\"    ");

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeReversed() {
		QueryLexer lexer = new QueryLexer("\"Ante\"=firstName");

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testOperationLiteral() {
		QueryLexer lexer = new QueryLexer("  \"Bosko\" ");
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, token.type);
		assertEquals("Bosko", token.value);
	}

	@Test
	public void testIvalidSignInOperationLiteraL() {
		QueryLexer lexer = new QueryLexer("  \"Bos!ko\" ");
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testNotClosedOperationLiteral() {
		QueryLexer lexer = new QueryLexer("  \"Bosko  ");
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testNotOpenOperationLiteral() {
		QueryLexer lexer = new QueryLexer("  Bosko\"  ");
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeLiteral() {
		QueryLexer lexer = new QueryLexer("  \"Bosko\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, token.type);
		assertEquals("Bosko", token.value);
	}

	@Test
	public void testLikeLiteralWithWildCardBeggining() {
		QueryLexer lexer = new QueryLexer("  \"*Bosko\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, token.type);
		assertEquals("*Bosko", token.value);
	}

	@Test
	public void testLikeLiteralWithWildCardMiddle() {
		QueryLexer lexer = new QueryLexer("  \"Bo*sko\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, token.type);
		assertEquals("Bo*sko", token.value);
	}

	@Test
	public void testLikeLiteralWithWildCardEnd() {
		QueryLexer lexer = new QueryLexer("  \"Bosko*\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, token.type);
		assertEquals("Bosko*", token.value);
	}

	@Test
	public void testLikeLiteralInvalidSign() {
		QueryLexer lexer = new QueryLexer("  \"Bo?sko\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeLiteralOpened() {
		QueryLexer lexer = new QueryLexer("  Bosko\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeLiteralNotClosed() {
		QueryLexer lexer = new QueryLexer(" \"Bosko ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeLiteralSpacesInLiteral() {
		QueryLexer lexer = new QueryLexer(" \"Bosk o\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLikeLiteralMultipleWC() {
		QueryLexer lexer = new QueryLexer(" \"B*osk*o\" ");
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);

		assertThrows(QueryParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testAnd() {
		QueryLexer lexer = new QueryLexer("aNd lastName");
		lexer.setMode(QueryLexerModes.AND_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.AND, token.type);
		assertEquals(null, token.value);
	}

	@Test
	public void testInvalidAnd() {
		QueryLexer lexer = new QueryLexer("and lastName");
		lexer.setMode(QueryLexerModes.AND_MODE);

		QueryToken token = lexer.nextToken();
		assertEquals(QueryTokenType.AND, token.type);
		assertEquals(null, token.value);
	}

	// ---------------------------------------------------------------------------
	// Test full queries.

	@Test
	public void testWholeQuery() {
		QueryLexer lexer = new QueryLexer(
				"firstName>     \"A\"and firstName<\"C\" and lastName LIKE\"B*ć\" and jmbag>=\"0000000002\"");

		QueryToken next = lexer.nextToken();
		assertEquals(QueryTokenType.GREATER, next.type);
		assertEquals("firstName", next.value);

		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, next.type);
		assertEquals("A", next.value);
		
		assertTrue(lexer.hasNextToken());
		lexer.setMode(QueryLexerModes.AND_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.AND, next.type);
		assertEquals(null, next.value);
		
		lexer.setMode(QueryLexerModes.ATRIBUT_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LESS, next.type);
		assertEquals("firstName", next.value);
		
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, next.type);
		assertEquals("C", next.value);
		
		assertTrue(lexer.hasNextToken());
		lexer.setMode(QueryLexerModes.AND_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.AND, next.type);
		assertEquals(null, next.value);
		
		lexer.setMode(QueryLexerModes.ATRIBUT_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LIKE, next.type);
		assertEquals("lastName", next.value);
		
		lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, next.type);
		assertEquals("B*ć", next.value);
		
		assertTrue(lexer.hasNextToken());
		lexer.setMode(QueryLexerModes.AND_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.AND, next.type);
		assertEquals(null, next.value);
		
		lexer.setMode(QueryLexerModes.ATRIBUT_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.GREATER_OR_EQUAL, next.type);
		assertEquals("jmbag", next.value);

		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);
		next = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, next.type);
		assertEquals("0000000002", next.value);
		
		assertFalse(lexer.hasNextToken());
	}
	
	@Test
	public void testWholeQuery2() {
		QueryLexer lexer = new QueryLexer(
				"jmbag=\"0000000003\"");

		QueryToken next = lexer.nextToken();
		assertEquals(QueryTokenType.EQUAL, next.type);
		assertEquals("jmbag", next.value);
		
		lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);
		 next = lexer.nextToken();
		assertEquals(QueryTokenType.LITERAL, next.type);
		assertEquals("0000000003", next.value);
		
		assertFalse(lexer.hasNextToken());
	}
}
