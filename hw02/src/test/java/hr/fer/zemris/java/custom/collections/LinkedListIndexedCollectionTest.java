package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection collection;

	@Test
	public void testDefaultConstructor() {
		collection = new LinkedListIndexedCollection();
		assertEquals(null, collection.getFirst());
		assertEquals(null, collection.getLast());
	}

	@Test
	public void testOtherContructor() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");

		LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection(collection);

		assertEquals(1, collection2.get(0));
		assertEquals("Boško", collection2.get(1));
		assertEquals("Ivek", collection2.get(2));
		// size
		assertEquals(3, collection2.size());

		// add empty collection
		collection.clear();
		collection2 = new LinkedListIndexedCollection(collection);
		assertEquals(null, collection2.getFirst());
		assertEquals(null, collection2.getLast());
		// size
		assertEquals(0, collection2.size());
	}

	@Test
	public void addArrayIndexCollToLinkedListCollThroughConstructor() {
		ArrayIndexCollection collectionToBeAdded = new ArrayIndexCollection();
		collectionToBeAdded.add("Ivek");
		collectionToBeAdded.add("Boško");

		collection = new LinkedListIndexedCollection(collectionToBeAdded);
		assertEquals("Ivek", collection.get(0));
		assertEquals("Boško", collection.get(1));

	}

	@Test
	public void addToCollection() {
		collection = new LinkedListIndexedCollection();
		// size
		assertEquals(0, collection.size());

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");
		collection.add(2.2);
		collection.add(1);

		// size
		assertEquals(5, collection.size());

		// indexes
		assertEquals(1, collection.get(0));
		assertEquals("Boško", collection.get(1));
		assertEquals("Ivek", collection.get(2));
		assertEquals(2.2, collection.get(3));
		assertEquals(1, collection.get(4));
		
		//add Null
		assertThrows(NullPointerException.class,()->{
			collection.add(null);
		});
	}
	

	@Test
	public void testGetOutOfBounds() {
		collection = new LinkedListIndexedCollection();
		
		collection.add(1);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(1);
		});
	}

	@Test
	public void testClear() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");

		collection.clear();
		assertEquals(null, collection.getFirst());
		assertEquals(null, collection.getLast());
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(0);
		});
		
		// size
		assertEquals(0, collection.size());
	}

	@Test
	public void testInsertion() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");

		collection.inserts("Prvi", 0);
		assertEquals("Prvi", collection.get(0));

		collection.inserts("Drugi", 2);
		assertEquals(1, collection.get(1));
		assertEquals("Drugi", collection.get(2));
		assertEquals("Boško", collection.get(3));

		collection.inserts("Treći", 5);
		assertEquals("Treći", collection.get(5));

		// size
		assertEquals(6, collection.size());

		// Exceptions
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.inserts("Ivek", -1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.inserts("Ivek", 7);
		});
		
		//adding null
		assertThrows(NullPointerException.class, () -> {
			collection.inserts(null, 2);
		});
	}

	@Test
	public void testIndexOf() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");

		assertEquals(-1, collection.indexOf(null));
		assertEquals(0, collection.indexOf(1));
		assertEquals(1, collection.indexOf("Boško"));
		assertEquals(2, collection.indexOf("Ivek"));
		assertEquals(-1, collection.indexOf(2.2));
	}

	@Test
	public void testRemoveByIndex() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");
		collection.add(2.2);

		collection.remove(0);
		assertEquals("Boško", collection.get(0));
		assertEquals("Ivek", collection.get(1));
		assertEquals(2.2, collection.get(2));
		// size
		assertEquals(3, collection.size());

		// insert 1 back at position 0
		collection.inserts(1, 0);
		collection.remove(1);
		assertEquals(1, collection.get(0));
		assertEquals("Ivek", collection.get(1));
		assertEquals(2.2, collection.get(2));
		// size
		assertEquals(3, collection.size());

		collection.inserts("Boško", 1);
		collection.remove(3);
		assertEquals("Ivek", collection.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(3);
		});
		// size
		assertEquals(3, collection.size());

		// IndexOutOfBounds
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.remove(-1);
			;
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.remove(3);
			;
		});
	}

	@Test
	public void testIsEmpty() {
		collection = new LinkedListIndexedCollection();

		assertEquals(true, collection.isEmpty());

		collection.add(1);
		assertEquals(false, collection.isEmpty());

		collection.remove(0);
		assertEquals(true, collection.isEmpty());
	}

	@Test
	public void testContains() {
		collection = new LinkedListIndexedCollection();

		collection.add(1);
		collection.add("Boško");

		assertEquals(true, collection.contains(1));
		assertEquals(true, collection.contains("Boško"));
		assertEquals(false, collection.contains("Ivek"));
		assertEquals(false, collection.contains(null));

	}

	@Test
	public void removeObjectValue() {
		collection = new LinkedListIndexedCollection();

		collection.add("Prvi");
		collection.add("Boško");
		collection.add("Ivek");
		collection.add("Pajo");

		assertEquals(true, collection.remove("Prvi"));
		assertEquals("Boško", collection.get(0));
		assertEquals("Ivek", collection.get(1));
		assertEquals("Pajo", collection.get(2));
		//size
		assertEquals(3, collection.size());

		collection.remove("Ivek");
		assertEquals("Boško", collection.get(0));
		assertEquals("Pajo", collection.get(1));
		
		collection.remove("Pajo");
		assertEquals("Boško", collection.get(0));
		assertThrows(IndexOutOfBoundsException.class, ()->{
			collection.get(1);
		});
		//size
		assertEquals(1, collection.size());
		
		assertEquals(false, collection.remove("Drugi"));
		assertEquals(false, collection.remove(null));
	}

	@Test
	public void testToArray() {
		collection = new LinkedListIndexedCollection();

		collection.add("Prvi");
		collection.add("Boško");
		collection.add("Ivek");

		Object[] array = collection.toArray();
		assertEquals("Prvi", array[0]);
		assertEquals("Boško", array[1]);
		assertEquals("Ivek", array[2]);
		assertEquals(3, array.length);

		assertEquals("Prvi", collection.get(0));
		assertEquals("Ivek", collection.get(2));

	}
	
	@Test
	public void addAllTest() {
		collection = new LinkedListIndexedCollection();
		collection.add("Prvi");
		collection.add("Boško");
		
		LinkedListIndexedCollection toBeAdded=new LinkedListIndexedCollection();
		toBeAdded.add("Štef");
		
		collection.addAll(toBeAdded);
		assertEquals("Prvi", collection.get(0));
		assertEquals("Boško", collection.get(1));
		assertEquals("Štef", collection.get(2));
		//size
		assertEquals(3, collection.size());
		
		assertEquals("Štef", toBeAdded.get(0));


		
	}

}
