package hr.fer.zemris.hw06.shell.nameFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.nameFilter.FilterResult;

public class FilterResultTest {

	@Test
	public void testPatternWithoutQoutes() {
		FilterResult filterResult = new FilterResult("boook/slika2-zagreb.jpg", "slika(\\d+)-([^.]+)\\.jpg");

		assertEquals("slika2-zagreb.jpg", filterResult.toString());
		assertEquals(2, filterResult.numberOfGroups());
		assertTrue(filterResult.isSatisfiable());
		assertEquals("slika2-zagreb.jpg", filterResult.group(0));
		assertEquals("2", filterResult.group(1));
		assertEquals("zagreb", filterResult.group(2));
	}
	
	@Test
	public void testPatternWithNoGroups() {
		FilterResult filterResult = new FilterResult("slika1-zagreb.jpg", "slika\\d+-[^.]+\\.jpg");

		assertEquals("slika1-zagreb.jpg", filterResult.toString());
		assertEquals(0, filterResult.numberOfGroups());
		assertTrue(filterResult.isSatisfiable());
		assertEquals("slika1-zagreb.jpg", filterResult.group(0));
	}

	@Test
	public void testPatternDoesNotMatch() {
		FilterResult filterResult = new FilterResult("boook/slika2-zagreb.jpg", "\"slika1000(\\d+)-([^.]+)\\.jpg\"");

		assertEquals("slika2-zagreb.jpg", filterResult.toString());
		assertEquals(-1, filterResult.numberOfGroups());
		assertFalse(filterResult.isSatisfiable());
	}
	
	@Test
	public void testFilterWithNoGroups() {
		try {
			List<FilterResult>result = FilterResult.filter(Paths.get("slike"), "slika\\d+-[^.]+\\.jpg");
			assertEquals(4, result.size());
			
			FilterResult zadar1=result.get(0);
			assertEquals("slika1-zadar.jpg", zadar1.group(0));
			assertEquals(0, zadar1.numberOfGroups());
			
			FilterResult zagreb1=result.get(1);
			assertEquals("slika1-zagreb.jpg", zagreb1.group(0));
			
			
		} catch (PatternSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
