package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementNull;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexerTest {

	// TESTS OF INSIDE TAGS

	@Test
	public void testVariblesAndEndTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("  var    v_ar2 var__2_  $}   ");

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("var")),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("v_ar2")),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("var__2_")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testNumberThenVariable() {
		SmartScriptLexer lexer = new SmartScriptLexer("  2v_ar12_12a $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(2)),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("v_ar12_12a")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testVariableAfterNegativeNumber() {
		SmartScriptLexer lexer = new SmartScriptLexer(" -1abc $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(-1)),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("abc")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testVariableAfterDecimalNumber() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 1.1abc $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(1.1)),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("abc")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testNoEndTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("  2 var    ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		assertEquals("2", ((Element) (lexer.nextToken().getValue())).asText());
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

	}

	@Test
	public void testStringsInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("    \"Strig12.?+\"  \"abcde\"  $}   ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"Strig12.?+\"")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"abcde\"")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}
	

	public void testNumbersInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 123 -12 12.2232 -1.212 0.001 $} ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(123)),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(-12)),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(12.2232)),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(-1.212)),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(0.001)),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidNumberFormation() {
		SmartScriptLexer lexer = new SmartScriptLexer("21..2 2.2. ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

	}

	@Test
	public void testNeverClosedString() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \"abcde  $}   ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

		SmartScriptLexer lexer2 = new SmartScriptLexer("  \"abcde    ");
		lexer2.setState(SmartScriptLexerState.TAG_STATE);
		assertThrows(SmartScriptParserException.class, () -> {
			lexer2.nextToken();
		});
	}

	@Test
	public void testFunctionsInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("@sin 12.12@c2_2asW$}   ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction("@sin")),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(12.12)),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction("@c2_2asW")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testFunctionsWithNoEndingOfTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" @1asd2 ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

		SmartScriptLexer lexer2 = new SmartScriptLexer(" @_agr ");
		lexer2.setState(SmartScriptLexerState.TAG_STATE);
		assertThrows(SmartScriptParserException.class, () -> {
			lexer2.nextToken();
		});

		SmartScriptLexer lexer3 = new SmartScriptLexer(" @assa_1221");
		lexer3.setState(SmartScriptLexerState.TAG_STATE);
		assertThrows(SmartScriptParserException.class, () -> {
			lexer3.nextToken();
		});
	}

	@Test
	public void testOperators() {
		SmartScriptLexer lexer = new SmartScriptLexer(" + - * ^ / -2 -");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		assertEquals("+", ((Element) (lexer.nextToken().getValue())).asText());
		assertEquals(SmartScriptTokenType.OPERATOR, lexer.getToken().getType());
		assertEquals("-", ((Element) (lexer.nextToken().getValue())).asText());
		assertEquals("*", ((Element) (lexer.nextToken().getValue())).asText());
		assertEquals("^", ((Element) (lexer.nextToken().getValue())).asText());
		assertEquals("/", ((Element) (lexer.nextToken().getValue())).asText());
		assertEquals("-2", ((Element) (lexer.nextToken().getValue())).asText());
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

	}

	@Test
	public void testOperatorsAndNumbers() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 1^2 $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(1)),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator("^")),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(2)),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);

	}
	
	

	@Test
	public void testAllInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" i-1.35bbb\"1\" $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(-1.35)),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("bbb")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"1\"")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testMoreAllInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" i i * @sin \"0.000\" @decfmt   $}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator("*")),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction("@sin")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"0.000\"")),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction("@decfmt")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEscapingInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("@sin  \"Some \\\\ test X\" $}   ");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction("@sin")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"Some \\\\ test X\"")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()), };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testMoreEscapingInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("\"Joe \\\"Long\\\" Smith\"$}");
		lexer.setState(SmartScriptLexerState.TAG_STATE);

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"Joe \\\"Long\\\" Smith\"")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };

		checkTokenStream(lexer, correctData);
	}

	// ------------------------------------------------------------------------------------------------------
	// Test outside of tags

	@Test
	public void testEscapings() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \\\\   \\{  ");
		;

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\\\\")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\\{")),
				new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidEscapingOOT() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \\ avcd ");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

		SmartScriptLexer lexer2 = new SmartScriptLexer(" \\");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer2.nextToken();
		});

		SmartScriptLexer lexer3 = new SmartScriptLexer(" \\a");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer3.nextToken();
		});
	}

	@Test
	public void testGettingValidTokens() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 1234 abcd 12.12 $} a1?!.A");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("1234")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("abcd")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("12.12")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("$}")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("a1?!.A")),
				new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testGettingWholeTextAfterEscaping() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } \\{$=1$} \\{$= fooor 1$}.Now ");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("Example")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("{")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("bla")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("}")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\\{$=1$}")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\\{$=")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("fooor")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("1$}.Now")),
				new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testNeverClosedTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$fOr i");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("fOr")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

	}

	// ----------------------------------------------------------------------------------------------------------------------
	// Test changing states
	@Test
	public void testEnteringTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("ab.12! {$for $}");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("ab.12!")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData3[] = { new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData3);
	}

	@Test
	public void testTagTextEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$for i $} book");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData3[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("book")),
				new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData3);
	}

	@Test
	public void testTwoTagsEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$for i $}     {$=i$}");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.VARIABLE,new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData3[] = { new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("=")) };
		checkTokenStream(lexer, correctData3);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable("i")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData4);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData5[] = { new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData5);
	}

	@Test
	public void testEscapedAndRealTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now {$=2$}");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("Example")),
				new SmartScriptToken(SmartScriptTokenType.STRING,  new ElementString("\\{$=1$}.")),
				new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString( "Now")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("="))};
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.NUMBER,new ElementConstantInteger(2)),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull())};
		checkTokenStream(lexer, correctData2);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData3[] = { new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull()) };
		checkTokenStream(lexer, correctData3);
	}

	@Test
	public void testEnteringTagAtBeggingn() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$foor$}");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("foor")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	public void testEOFAfterTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$foor  ");
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		assertThrows(SmartScriptParserException.class, ()->{
			lexer.nextToken();
		});
	}


	@Test
	public void testInvalidTagName() {
		SmartScriptLexer lexer = new SmartScriptLexer(" {$_abcd  ");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});

		SmartScriptLexer lexer2 = new SmartScriptLexer(" {$1abcd  ");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer2.nextToken();
		});
	}

	@Test
	public void testWorkingBothStatesTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 12ab {$ FOR \"Bo\\\"o\\\"k\" $}  {$   = a$}");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("12ab")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("FOR")) };
		checkTokenStream(lexer, correctData);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData2[] = { new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("\"Bo\\\"o\\\"k\"")),
				new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull()) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE);
		SmartScriptToken correctData3[] = { new SmartScriptToken(SmartScriptTokenType.START_OF_TAG,new ElementVariable("=")), };
		checkTokenStream(lexer, correctData3);

		lexer.setState(SmartScriptLexerState.TAG_STATE);
		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.VARIABLE,new ElementVariable("a")), };
		checkTokenStream(lexer, correctData4);

	}

	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue().asText(), (actual.getValue()).asText(), msg);
			counter++;
		}
	}

	@Test
	public void testTagAfterNewLine() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n{$ for $}");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\r\n")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for"))
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	public void testTagAfterTab() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \n\r\t{$ for $}");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\n\r\t")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for"))
		};
		checkTokenStream(lexer, correctData4);
	}
	@Test
	public void testTagAfterCarriage() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r{$ for $}");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\r")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for"))
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	public void testTagAfetSgn() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("  sin{$ for $}");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("sin")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for"))
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	public void testTagAfterSpacesSgn() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("  sin {$ for $}");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("sin")),
				new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("for"))
		};
		checkTokenStream(lexer, correctData4);
	}
	
	// -------------------------------------------------------------------------------------------------------
	// MC TESTS

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returnedq different token than nextToken.");
	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("       \r     \n\t      \r    \n    ");

		SmartScriptToken correctData4[] = { new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\r")),
				 new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\n\t")),
				 new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\r")),
				 new SmartScriptToken(SmartScriptTokenType.STRING,new ElementVariable("\n"))};
		checkTokenStream(lexer, correctData4);
	}
	
	
	
	

}
