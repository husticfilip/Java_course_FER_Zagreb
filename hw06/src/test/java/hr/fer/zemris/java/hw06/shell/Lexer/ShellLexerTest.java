package hr.fer.zemris.java.hw06.shell.Lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ShellLexerTest {
	
	@Test
	public void testPath() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "   /home/john/info.txt    /home/john/backupFolder  ");
		assertEquals("/home/john/info.txt", lexer.nextToken().getValue());
		assertTrue(lexer.hasNext());
		ShellToken token =lexer.nextToken();
		assertEquals("/home/john/backupFolder",token.getValue());
		assertEquals(ShellTokenType.PATH,token.getType());
		assertFalse(lexer.hasNext());

	}
	
	@Test
	public void testQuotesPath() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\korekcije.txt\"");
		assertEquals("C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\korekcije.txt", lexer.nextToken().getValue());
	}
	
	@Test
	public void testEscapingBackslash() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"C:\\Program Files/info.text\"");
		assertEquals("C:\\Program Files/info.text", lexer.nextToken().getValue());
	}
	
	@Test
	public void testEscapingQuotes() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"C: \\\"Program\\\" Files/info.text\"");
		assertEquals("C: \"Program\" Files/info.text", lexer.nextToken().getValue());
	}
	
	@Test
	public void testNoEscapingChars() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"  C:Program        as/\\abc \"");
		assertEquals("C:Program        as/\\abc", lexer.nextToken().getValue());
	}
	
	@Test
	public void testNotClosedQuotes() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"C:Program ");
		assertThrows(ShellLexerException.class, ()->{
			lexer.nextToken();
		});
	}

	@Test
	public void testNullBody() {
		assertThrows(NullPointerException.class, ()->{
			new ShellLexer(LexerModes.PATH_MODE, null);
		});
	}
	
	@Test
	public void testNextName() {
		ShellLexer lexer = new ShellLexer(LexerModes.NAME_MODE, "direktorij123!?.");
		ShellToken token = lexer.nextToken();
		assertEquals("direktorij123!?.", token.getValue());
		assertEquals(ShellTokenType.NAME, token.getType());
		
	}
	
	@Test
	public void testInvalidDirectory() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "c:dir\\");

		assertThrows(ShellLexerException.class, ()->{
			lexer.nextToken();
		});
	}
	
	@Test
	public void testInvalidDirectory2() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "c:dir:");

		assertThrows(ShellLexerException.class, ()->{
			lexer.nextToken();
		});
	}
	
	@Test
	public void testInvalidDirectoryQuotes() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"c:dir\\\"");

		assertThrows(ShellLexerException.class, ()->{
			lexer.nextToken();
		});
	}
	
	@Test
	public void testInvalidDirectoryQoutes2() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"c:dir:\"");

		assertThrows(ShellLexerException.class, ()->{
			lexer.nextToken();
		});
	}
	
	@Test
	public void testHasNextAfterPath() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "  c:dir:\\a   ");
		
		assertTrue(lexer.hasNext());
		assertEquals("c:dir:\\a", lexer.nextToken().getValue());
		assertFalse(lexer.hasNext());
	}
	
	@Test
	public void testHasNextAfterQuotesPath() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "  \"c:dir: nesto   \\a  \"   ");
		
		assertTrue(lexer.hasNext());
		assertEquals("c:dir: nesto\\a", lexer.nextToken().getValue());
		assertFalse(lexer.hasNext());
	}
	
	@Test
	public void testHasNextAfterName() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, " ime    drugo  ");
		
		assertTrue(lexer.hasNext());
		assertEquals("ime", lexer.nextToken().getValue());
		assertTrue(lexer.hasNext());
		assertEquals("drugo", lexer.nextToken().getValue());
		assertFalse(lexer.hasNext());
	}
	
	@Test
	public void testCombination() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, " C:path1\\a  \"C:path2\\ drugi da  \"   argument  ");
		assertEquals("C:path1\\a", lexer.nextToken().getValue());
		assertEquals("C:path2\\ drugi da", lexer.nextToken().getValue());
		assertTrue(lexer.hasNext());
		lexer.setMode(LexerModes.NAME_MODE);
		assertEquals("argument", lexer.nextToken().getValue());
	}
	
	
	@Test
	public void testSpacesBeforeBackSpace() {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, "\"direktorij123  \\  abc    \"");
		ShellToken token = lexer.nextToken();
		assertEquals("direktorij123\\  abc", token.getValue());
	}
}
