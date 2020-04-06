package hr.fer.zemris.java.cutom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.java.custom.collections.Tester;

public class DictionaryTest {

	@Test
	public void testWhenEmpty() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		assertTrue(dictionary.isEmpty());
		assertEquals(0, dictionary.size());
	}

	@Test
	public void testWhenIsNotEmpty() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		assertFalse(dictionary.isEmpty());
	}

	@Test
	public void testSize() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		assertEquals(3, dictionary.size());
	}

	@Test
	public void testClear() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		dictionary.clear();
		assertEquals(0, dictionary.size());
		assertTrue(dictionary.isEmpty());
	}

	@Test
	public void testGet() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		assertEquals(0, dictionary.get("Ivek"));
		assertEquals(0, dictionary.get("Štef"));
		assertEquals(7, dictionary.get("Boško"));
	}

	@Test
	public void testGetValuNotInDict() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		assertNull(dictionary.get("Fico"));
		assertNull(dictionary.get(12.12));

	}

	@Test
	public void testPutPairWithExistingKey() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);

		dictionary.put("Štef", 23);

		assertEquals(0, dictionary.get("Ivek"));
		assertEquals(23, dictionary.get("Štef"));
		assertEquals(7, dictionary.get("Boško"));

		assertEquals(3, dictionary.size());
	}
	
	@Test
	public void testKeyNull() {
		String[] keys = { "Ivek", "Štef", "Boško" };
		Integer[] values = { 0, 0, 7 };
		Dictionary<String, Integer> dictionary = initDictionary(keys, values);
		
		assertThrows(IllegalArgumentException.class, ()->{
			dictionary.put(null, 11);
		});
	}
	
	public <K, V> Dictionary<K, V> initDictionary(K[] keys, V[] values) {

		Dictionary<K, V> dictionary = new Dictionary<K, V>();
		for (int i = 0; i < keys.length; ++i) {
			dictionary.put(keys[i], values[i]);
		}

		return dictionary;
	}
}
