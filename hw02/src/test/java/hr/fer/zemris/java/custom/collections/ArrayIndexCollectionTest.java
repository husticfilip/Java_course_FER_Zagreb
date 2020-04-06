package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArrayIndexCollectionTest {

	private ArrayIndexCollection collection;

	@Test
	public void defaultConstructor() {
		collection = new ArrayIndexCollection();
		assertEquals(16, collection.getCapacity());
	}

	@Test
	public void defaultConstructorElementsSize() {
		collection = new ArrayIndexCollection();
		assertEquals(16, collection.getElements().length);
	}

	@Test
	public void capacityConstructor() {
		collection = new ArrayIndexCollection(50);
		assertEquals(50, collection.getCapacity());
	}

	@Test
	public void illegalCapacityConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexCollection(0);
		});
	}

	@Test
	public void illegalCapacityConstructor2() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexCollection(-1);
		});
	}

	@Test
	public void additionalParametarConstructor() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");

		ArrayIndexCollection collection2 = new ArrayIndexCollection(collection);

		assertEquals(1, collection2.get(0));
		assertEquals("Ivan", collection2.get(1));
		// size
		assertEquals(2, collection2.size());

		// null as colletion
		assertThrows(NullPointerException.class, () -> {
			new ArrayIndexCollection(null);
		});
		assertThrows(NullPointerException.class, () -> {
			new ArrayIndexCollection(null, 10);
		});
	}

	@Test
	public void LinkedListCollectionIntoArrayIndexCollectionConstructor() {
		LinkedListIndexedCollection linkedCollection = new LinkedListIndexedCollection();
		linkedCollection.add("Ivek");
		linkedCollection.add("Boško");

		collection = new ArrayIndexCollection(linkedCollection);
		assertEquals("Ivek", collection.get(0));
		assertEquals("Boško", collection.get(1));

		// size
		assertEquals(2, collection.size());

	}

	@Test
	public void additionalParametarConstructorAndInitialSize() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");
		collection.add("Boško");

		ArrayIndexCollection collection2 = new ArrayIndexCollection(collection, 1);
		assertEquals(1, collection2.get(0));
		assertEquals("Ivan", collection2.get(1));
		assertEquals("Boško", collection2.get(2));
		// size
		assertEquals(3, collection2.size());

	}

	@Test
	public void addingElementsToCollection() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");
		assertEquals(1, collection.get(0));
		assertEquals("Ivan", collection.get(1));

		// size
		assertEquals(2, collection.size());

		// add null
		assertThrows(NullPointerException.class, () -> {
			collection.add(null);
		});
	}

	@Test
	public void addingElementsOverCapacity() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");
		collection.add("Boško");

		// is Boško on index 2
		assertEquals("Boško", collection.get(2));

		// capacity
		assertEquals(4, collection.getCapacity());

		// size
		assertEquals(3, collection.size());
	}

	@Test
	public void addNull() {
		collection = new ArrayIndexCollection(2);
		assertThrows(NullPointerException.class, () -> {
			collection.add(null);
		});
	}

	@Test
	public void getAtIllegalIndex() {
		collection = new ArrayIndexCollection(2);
		collection.add("Boško");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(1);
		});
	}

	@Test
	public void checkingSize() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");
		collection.add("Boško");
		assertEquals(3, collection.size());
	}

	@Test
	public void testClear() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Ivan");
		collection.add("Boško");
		collection.clear();
		// get element at position 0 after clear
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.get(0);
		});

		// capacity
		assertEquals(4, collection.getCapacity());

		// size
		assertEquals(0, collection.size());
	}

	@Test
	public void testInsert() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.inserts("Boško", 0);
		assertEquals("Boško", collection.get(0));
		assertEquals(1, collection.get(1));

		collection.inserts(2.2, 1);
		collection.inserts("Ivan", 3);
		assertEquals(2.2, collection.get(1));
		assertEquals(1, collection.get(2));
		assertEquals("Ivan", collection.get(3));

		// size
		assertEquals(4, collection.size());
		// capacity
		assertEquals(4, collection.getCapacity());
	}

	@Test
	public void illeglaIndexInsert() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.inserts("Boško", -1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.inserts("Boško", 2);
		});
	}
	
	@Test
	public void insertNull() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);

		assertThrows(NullPointerException.class, () -> {
			collection.inserts(null, 0);
		});
	}

	@Test
	public void indexOf() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Boško");
		collection.add(1);
		collection.add("Ivek");

		assertEquals(0, collection.indexOf(1));
		assertEquals(1, collection.indexOf("Boško"));
		assertEquals(3, collection.indexOf("Ivek"));
		assertEquals(-1, collection.indexOf(null));
		assertEquals(-1, collection.indexOf(2.2));
	}

	@Test
	public void removeAtIndex() {
		collection = new ArrayIndexCollection(2);
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

		collection.remove(1);
		assertEquals("Boško", collection.get(0));
		;
		assertEquals(2.2, collection.get(1));
		// size
		assertEquals(2, collection.size());

		collection.remove(1);
		assertEquals("Boško", collection.get(0));
		;
		// size
		assertEquals(1, collection.size());
	}

	@Test
	public void removeAtIllegalIndex() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.remove(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			collection.remove(1);
		});
	}

	@Test
	public void testIsEmpty() {
		collection = new ArrayIndexCollection(2);
		assertEquals(true, collection.isEmpty());

		collection.add(1);
		assertEquals(false, collection.isEmpty());
	}

	@Test
	public void testContains() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);

		assertEquals(true, collection.contains(1));
		assertEquals(false, collection.contains(2));
		assertEquals(false, collection.contains(null));
	}

	@Test
	public void removeGivenObject() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");
		collection.add(2.2);

		assertEquals(true, collection.remove(Integer.valueOf(1)));
		assertEquals("Boško", collection.get(0));
		assertEquals("Ivek", collection.get(1));
		assertEquals(2.2, collection.get(2));
		// size
		assertEquals(3, collection.size());

		assertEquals(true, collection.remove(2.2));
		assertEquals("Boško", collection.get(0));
		assertEquals("Ivek", collection.get(1));
		// size
		assertEquals(2, collection.size());

		assertEquals(false, collection.remove("Štef"));
		assertEquals(false, collection.remove(null));
	}

	@Test
	public void testToArray() {
		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add("Boško");
		collection.add("Ivek");

		Object[] array = collection.toArray();
		assertEquals(1, array[0]);
		assertEquals("Boško", array[1]);
		assertEquals("Ivek", array[2]);

		// size
		assertEquals(3, array.length);
	}

	@Test
	public void testToArrayWithEmptyCollection() {
		collection = new ArrayIndexCollection(2);

		Object[] array = collection.toArray();
		// size
		assertEquals(0, array.length);

	}

	@Test
	public void addAllFromOtherCollection() {
		ArrayIndexCollection collectionToBeAdded = new ArrayIndexCollection(2);
		collectionToBeAdded.add("Ivek");
		collectionToBeAdded.add("Boško");

		collection = new ArrayIndexCollection(2);
		collection.add(1);
		collection.add(2);

		// adding
		collection.addAll(collectionToBeAdded);

		// checking first collection
		assertEquals(1, collection.get(0));
		assertEquals(2, collection.get(1));
		assertEquals("Ivek", collection.get(2));
		assertEquals("Boško", collection.get(3));
		// size
		assertEquals(4, collection.size());

		// checking added colleciton
		assertEquals("Ivek", collectionToBeAdded.get(0));
		assertEquals("Boško", collectionToBeAdded.get(1));
		// size
		assertEquals(2, collectionToBeAdded.size());

	}
}
