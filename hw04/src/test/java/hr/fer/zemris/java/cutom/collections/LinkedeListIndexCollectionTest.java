package hr.fer.zemris.java.cutom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

public class LinkedeListIndexCollectionTest {
	
	
	
	@Test
	public void testAddandGetIndex() {
		LinkedListIndexedCollection<String> collection = new LinkedListIndexedCollection<String>();
		collection.add("Ivek");
		collection.add("Boško");
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Boško");
	}
	
	@Test
	public void testConstructor() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> coll = initCollection(values);
		
		LinkedListIndexedCollection<String> collection= new LinkedListIndexedCollection<String>(coll);
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Boško");
	}
	
	@Test
	public void testInsert() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		collection.insert("Štef", 1);
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Štef");
		assertEquals(collection.get(2), "Boško");
	}
	
	@Test
	public void testIndexOf() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		assertEquals(1, collection.indexOf("Boško"));
		assertEquals(-1, collection.indexOf(2));
	}
	
	@Test
	public void testRemove() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		collection.remove("Ivek");
		assertEquals(collection.get(0), "Boško");
	}
	
	@Test
	public void testToArray() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		Object array[]= collection.toArray();
		assertEquals(array[0], "Ivek");
		assertEquals(array[1], "Boško");
	}
	
	@Test
	public void testContains() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		assertTrue(collection.contains("Ivek"));

		assertFalse(collection.contains(2.22));
	}
	
	@Test
	public void testElementsGetter() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
	
		ElementsGetter<String> getter= collection.createElementsGetter();
		
		assertEquals("Ivek", getter.getNextElement());
		assertEquals("Boško", getter.getNextElement());
		
		assertFalse(getter.hasNextElement());
	}
	
	@Test
	public void testForEach() {
		String[] values = {"Ivek","Boško"};
		LinkedListIndexedCollection<String> collection = initCollection(values);
		
		Processor<String> proc = e -> System.out.println(e);
		
		collection.forEach(proc);
	}
	
	
	
	public static <T> LinkedListIndexedCollection<T> initCollection(T[] elements){
		LinkedListIndexedCollection<T> coll = new LinkedListIndexedCollection<T>();
		
		for(T element : elements) {
			coll.add(element);
		}
		
		return coll;
	}
	

}
