package hr.fer.zemris.java.cutom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.Processor;
import hr.fer.zemris.java.custom.collections.Tester;

public class ArrayIndexedCollectionTest {
	
	
	@Test
	public void testAddandGetIndex() {
		ArrayIndexedCollection<String> collection = new ArrayIndexedCollection<String>();
		collection.add("Ivek");
		collection.add("Boško");
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Boško");
	}
	
	@Test
	public void testConstructor() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> coll = initCollection(values);
		
		ArrayIndexedCollection<String> collection= new ArrayIndexedCollection<String>(coll);
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Boško");
	}
	
	@Test
	public void testInsert() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
		
		collection.insert("Štef", 1);
		assertEquals(collection.get(0), "Ivek");
		assertEquals(collection.get(1), "Štef");
		assertEquals(collection.get(2), "Boško");
	}
	
	@Test
	public void testIndexOf() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
		
		assertEquals(1, collection.indexOf("Boško"));
		assertEquals(-1, collection.indexOf(2));
	}
	
	@Test
	public void testRemove() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
		
		collection.remove("Ivek");
		assertEquals(collection.get(0), "Boško");
	}
	
	@Test
	public void testToArray() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
		
		Object array[]= collection.toArray();
		assertEquals(array[0], "Ivek");
		assertEquals(array[1], "Boško");
	}
	
	@Test
	public void testContains() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
		
		assertTrue(collection.contains("Ivek"));

		assertFalse(collection.contains(2.22));
	}
	
	@Test
	public void testElementsGetter() {
		String[] values = {"Ivek","Boško"};
		ArrayIndexedCollection<String> collection = initCollection(values);
	
		ElementsGetter<String> getter= collection.createElementsGetter();
		
		assertEquals("Ivek", getter.getNextElement());
		assertEquals("Boško", getter.getNextElement());
		
		assertFalse(getter.hasNextElement());
	}
	
//	@Test
//	public void testForEach() {
//		String[] values = {"Ivek","Boško"};
//		ArrayIndexedCollection<String> collection = initCollection(values);
//		
//		Processor<String> proc = e -> System.out.println(e);
//		
//		collection.forEach(proc);
//	}
	
	@Test
	public void addAllSatisfying() {
		Integer[] values = {Integer.valueOf(1),Integer.valueOf(2),Integer.valueOf(3),Integer.valueOf(4)};
		ArrayIndexedCollection<Integer> collection = initCollection(values);
		
		Tester<Integer> tester = t ->{
			return t % 2 == 0 ? true : false;
		};
		
		ArrayIndexedCollection<Integer> collection2=new ArrayIndexedCollection<Integer>();
		collection2.addAllSatisfying(collection, tester);
		
		assertEquals(Integer.valueOf(2), collection2.get(0));
		assertEquals(Integer.valueOf(4), collection2.get(1));
	}
	
	@Test
	public void testAddingExtendedClasses() {
		ArrayIndexedCollection<Number> collection = new ArrayIndexedCollection<>();
		
		collection.add(Integer.valueOf(2));
		collection.add(Double.valueOf(5));
		assertEquals(Integer.valueOf(2), collection.get(0));
		assertEquals(Double.valueOf(5), collection.get(1));
	}
	
	@Test
	public void testAddAllExtendedClasses() {
		ArrayIndexedCollection<Number> collection = new ArrayIndexedCollection<>();
		collection.add(Integer.valueOf(2));
		collection.add(Double.valueOf(5));
		
		ArrayIndexedCollection<Number> collection2 = new ArrayIndexedCollection<>();
		collection2.addAll(collection);
		assertEquals(Integer.valueOf(2), collection2.get(0));
		assertEquals(Double.valueOf(5), collection2.get(1));
	}
	
	
	public static <T> ArrayIndexedCollection<T> initCollection(T[] elements){
		ArrayIndexedCollection<T> coll = new ArrayIndexedCollection<T>();
		
		for(T element : elements) {
			coll.add(element);
		}
		
		return coll;
	}
	

}
