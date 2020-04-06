package hr.fer.zemris.hw06.shell.nameFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.nameFilter.FilterResult;
import hr.fer.zemris.java.hw06.shell.nameFilter.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.nameFilter.NameParserException;

public class NameBuilderParserTest {

	@Test
	public void testBuilderParser() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.jpg");
		FilterResult result= new FilterResult("DIR1/slika1-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("gradovi-zagreb-001.jpg", sb.toString());
	}
	
	@Test
	public void testBuilderParserSubAtEnd() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${1,03}.jpg${1,3}");
		FilterResult result= new FilterResult("DIR1/slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("gradovi-010.jpg 10", sb.toString());
	}
	
	@Test
	public void testBuilderParserSubAtBeggingin() {
		NameBuilderParser parser = new NameBuilderParser("${2,03}-gradovi-.jpg${1,3}");
		FilterResult result= new FilterResult("slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("zagreb-gradovi-.jpg 10", sb.toString());
	}
	
	@Test
	public void testBuilderParserSpacePadding() {
		NameBuilderParser parser = new NameBuilderParser("${2,10}-gradovi");
		FilterResult result= new FilterResult("slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("    zagreb-gradovi", sb.toString());
	}
	
	@Test
	public void testBuilderParserZeroPadding() {
		NameBuilderParser parser = new NameBuilderParser("${2,010}-gradovi");
		FilterResult result= new FilterResult("slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("0000zagreb-gradovi", sb.toString());
	}
	
	@Test
	public void testBuilderParserSpaceInSubs() {
		NameBuilderParser parser = new NameBuilderParser("${   2   ,    010     }-gradovi");
		FilterResult result= new FilterResult("slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("0000zagreb-gradovi", sb.toString());
	}
	
	@Test
	public void testBuilderParserSpaceInSubs2() {
		NameBuilderParser parser = new NameBuilderParser("${   2   }-gradovi");
		FilterResult result= new FilterResult("slika10-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");
		StringBuilder sb = new StringBuilder();
		parser.getNameBuilder().execute(result, sb);
		assertEquals("zagreb-gradovi", sb.toString());
	}
	
	@Test
	public void testBuilderParserCharInSubs() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${2,0a10}-gradovi");		
		});
	}
	
	@Test
	public void testBuilderSpaceBetweenNums() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${2,1 1}-gradovi");		
		});
	}
	
	@Test
	public void testBuilderInvalidSignInSubs() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${$2}-gradovi");		
		});
	}
	
	@Test
	public void testBuilderEmptyBrackets() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${}-gradovi");		
		});
	}
	
	@Test
	public void testBuilderNotClosedBrackets() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${-gradovi");		
		});
	}
	
	@Test
	public void testBuilderMultipleExpr() {
		assertThrows(NameParserException.class, ()->{
			 new NameBuilderParser("${1,2,3}-gradovi");		
		});
	}
	

	
}
