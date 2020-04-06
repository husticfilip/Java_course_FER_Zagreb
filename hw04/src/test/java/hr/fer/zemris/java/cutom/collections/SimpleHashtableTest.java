package hr.fer.zemris.java.cutom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;
import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	@Test
	public void testInvaludConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new SimpleHashtable<>(0);
		});
	}

	@Test
	public void testPutOneValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Boško", 2);
		assertEquals(2, table.get("Boško"));
	};

	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Integer> examMarks = initTable();

		assertTrue(examMarks.containsKey("Ivana"));
		assertTrue(examMarks.containsKey("Ante"));
		assertTrue(examMarks.containsKey("Jasna"));
		assertTrue(examMarks.containsKey("Kristina"));

		assertFalse(examMarks.containsKey("Fico"));
		assertFalse(examMarks.containsKey(2));
		assertFalse(examMarks.containsKey(null));
	}

	@Test
	public void testNullKey() {
		SimpleHashtable<String, Integer> table = initTable();

		assertThrows(NullPointerException.class, () -> {
			table.put(null, 2);
		});
	}

	@Test
	public void testGetValues() {
		SimpleHashtable<String, Integer> table = initTable();

		assertEquals(2, table.get("Ivana"));
		assertEquals(2, table.get("Ante"));
		assertEquals(2, table.get("Jasna"));
		assertEquals(5, table.get("Kristina"));
	}

	@Test
	public void testChangeValues() {
		SimpleHashtable<String, Integer> table = initTable();

		table.put("Ivana", 1);
		assertEquals(1, table.get("Ivana"));
		table.put("Kristina", 3);
		assertEquals(3, table.get("Kristina"));
		assertEquals(4, table.size());
	}

	@Test
	public void testGetNullValue() {
		SimpleHashtable<String, Integer> table = initTable();

		table.put("Josko", null);
		assertEquals(null, table.get("Josko"));
	}

	@Test
	public void testGetValueNotIntable() {
		SimpleHashtable<String, Integer> table = initTable();

		assertEquals(null, table.get("Josko"));
	}

	@Test
	public void testGetNullKey() {
		SimpleHashtable<String, Integer> table = initTable();

		assertEquals(null, table.get(null));
	}

	@Test
	public void testSize() {
		SimpleHashtable<String, Integer> table = initTable();

		assertEquals(4, table.size());

		table.put("Josko", 1);
		assertEquals(5, table.size());
	}

	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> table = initTable();

		assertTrue(table.containsValue(2));
		assertTrue(table.containsValue(5));
	}

	@Test
	public void testDoesNotContanValue() {
		SimpleHashtable<String, Integer> table = initTable();

		assertFalse(table.containsValue(1));
		assertFalse(table.containsValue(null));
	}

	@Test
	public void testContanNullValue() {
		SimpleHashtable<String, Integer> table = initTable();

		table.put("Josko", null);
		assertTrue(table.containsValue(null));
	}

	@Test
	public void testRemoveFromBeggignOfSlot() {
		SimpleHashtable<String, Integer> table = initTable();

		table.remove("Ivana");
		assertFalse(table.containsKey("Ivana"));
		assertTrue(table.containsKey("Ante"));
		assertTrue(table.containsKey("Jasna"));
		assertTrue(table.containsKey("Kristina"));
		assertEquals(3, table.size());
	}

	@Test
	public void testRemoveFromMiddleOfSlot() {
		SimpleHashtable<String, Integer> table = initTable();

		table.remove("Jasna");
		assertTrue(table.containsKey("Ivana"));
		assertTrue(table.containsKey("Ante"));
		assertFalse(table.containsKey("Jasna"));
		assertTrue(table.containsKey("Kristina"));
		assertEquals(3, table.size());
	}

	@Test
	public void testRemoveFromEndOfSlot() {
		SimpleHashtable<String, Integer> table = initTable();

		table.remove("Kristina");
		assertTrue(table.containsKey("Ivana"));
		assertTrue(table.containsKey("Ante"));
		assertTrue(table.containsKey("Jasna"));
		assertFalse(table.containsKey("Kristina"));
		assertEquals(3, table.size());
	}

	@Test
	public void testRemoveNul() {
		SimpleHashtable<String, Integer> table = initTable();

		table.remove(null);
		assertTrue(table.containsKey("Ivana"));
		assertTrue(table.containsKey("Ante"));
		assertTrue(table.containsKey("Jasna"));
		assertTrue(table.containsKey("Kristina"));
		assertEquals(4, table.size());
	}

	@Test
	public void testRemoveOnlyEntryInSlot() {
		SimpleHashtable<String, Integer> table = initTable();

		table.remove("Ante");
		assertFalse(table.containsKey("Ante"));
		assertEquals(3, table.size());
	}

	@Test
	public void testIsEmpty() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(5);

		assertTrue(table.isEmpty());
		table.put("Ivana", 5);
		assertFalse(table.isEmpty());
		table.remove("Ivana");
		assertTrue(table.isEmpty());

	}

//	@Test
//	public void testToString() {
//		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();
//		System.out.println(table.toString());
//		
//		
//		table.put("Bosko", 5);
//		System.out.println(table.toString());
//		
//		table=initTable();
//		System.out.println(table.toString());	
//	}

	@Test
	public void testDoubleSlots() {
		SimpleHashtable<String, Integer> table = initTable();

		table.put("a", 1);
		table.put("b", 1);

		assertTrue(table.containsKey("a"));
		assertTrue(table.containsKey("b"));
		assertEquals(6, table.size());

	}

	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> table = initTable();

		table.clear();
		assertEquals(0, table.size());
		assertFalse(table.containsKey("Ante"));
	}

	@Test
	public void testNext() {
		SimpleHashtable<String, Integer> table = initTable();
		table.put("a", 1);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertEquals("a", iterator.next().getKey());
		assertEquals("Ante", iterator.next().getKey());
		assertEquals("Ivana", iterator.next().getKey());
		assertEquals("Jasna", iterator.next().getKey());
		assertEquals("Kristina", iterator.next().getKey());
		assertThrows(NoSuchElementException.class, () -> {
			iterator.next().getKey();
		});
	}

	@Test
	public void testRemoveOnlySlotsValue() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertEquals("Ante", iterator.next().getKey());
		iterator.remove();
		assertFalse(table.containsKey("Ante"));
		assertEquals("Ivana", iterator.next().getKey());
		assertEquals("Jasna", iterator.next().getKey());
		assertEquals("Kristina", iterator.next().getKey());
	}

	@Test
	public void testRemoveFirstSlotsValue() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertEquals("Ante", iterator.next().getKey());
		assertEquals("Ivana", iterator.next().getKey());
		iterator.remove();
		assertFalse(table.containsKey("Ivana"));
		assertEquals("Jasna", iterator.next().getKey());
		assertEquals("Kristina", iterator.next().getKey());
	}

	@Test
	public void testRemoveMiddleSlotsValue() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertEquals("Ante", iterator.next().getKey());
		assertEquals("Ivana", iterator.next().getKey());
		assertEquals("Jasna", iterator.next().getKey());
		iterator.remove();
		assertFalse(table.containsKey("Jasna"));
		assertEquals("Kristina", iterator.next().getKey());
	}

	@Test
	public void testRemoveLastSlotsValue() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertEquals("Ante", iterator.next().getKey());
		assertEquals("Ivana", iterator.next().getKey());
		assertEquals("Jasna", iterator.next().getKey());
		assertEquals("Kristina", iterator.next().getKey());
		iterator.remove();
		assertFalse(table.containsKey("Kristina"));
	}

	@Test
	public void testRemoveAfterNoNextcalls() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertThrows(IllegalStateException.class, () -> {
			iterator.remove();
		});
	}

	@Test
	public void testCallRemoveTwoTimesInARow() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		iterator.next();
		iterator.remove();
		assertThrows(IllegalStateException.class, () -> {
			iterator.remove();
		});
	}

	@Test
	public void removeAll() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		iterator.next();
		iterator.remove();
		assertFalse(table.containsKey("Ante"));

		iterator.next();
		iterator.remove();
		assertFalse(table.containsKey("Ivana"));

		iterator.next();
		iterator.remove();
		assertFalse(table.containsKey("Jasna"));

		iterator.next();
		iterator.remove();
		assertFalse(table.containsKey("Kristina"));

		assertEquals(0, table.size());
	}

	@Test
	public void testHasNext() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		assertTrue(iterator.hasNext());

		assertEquals("Ante", iterator.next().getKey());
		assertTrue(iterator.hasNext());

		assertEquals("Ivana", iterator.next().getKey());
		assertTrue(iterator.hasNext());

		assertEquals("Jasna", iterator.next().getKey());
		assertTrue(iterator.hasNext());

		assertEquals("Kristina", iterator.next().getKey());
		assertFalse(iterator.hasNext());

	}

	// ITERATOR MODIFICATION TESTING
	// --------------------------------------------------------

	@Test
	public void testNewValueAdded() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();

		table.put("Bosko", 2);
		assertThrows(ConcurrentModificationException.class, () -> {
			iterator.next();
		});
		assertThrows(ConcurrentModificationException.class, () -> {
			iterator.hasNext();
		});
	}

	@Test
	public void testSizeOfTableChanged() {
		SimpleHashtable<String, Integer> table = initTable();

		table.put("Bosko", 2);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		table.put("Ivek", 1);
		
		assertThrows(ConcurrentModificationException.class, () -> {
			iterator.next();
		});
	}
	
	@Test
	public void testValueOfEntryChanged() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		
		table.put("Ante", 5);
		TableEntry<String, Integer> next= iterator.next();
		assertEquals("Ante", next.getKey());
		assertEquals(5, next.getValue());
		assertTrue(iterator.hasNext());
		assertEquals("Ivana", iterator.next().getKey());
	}
	
	@Test
	public void testRemoveAfterChange() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		
		iterator.next();
		table.put("Bosko", 1);
		assertThrows(ConcurrentModificationException.class, () -> {
			iterator.remove();
		});
	}

	@Test
	public void testRemoveAfterValueOfEntryChanged() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		
		table.put("Ante", 5);
		iterator.next();
		iterator.remove();
		
		assertEquals(3, table.size());
	}
	
	@Test
	public void testRemoveOutsideOfIterator() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		
		table.remove("Ivana");
		assertThrows(ConcurrentModificationException.class, ()->{
			iterator.next();
		});
	}
	
	@Test
	public void testRemoveOutsideOfIterator2() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		
		iterator.next();
		table.remove("Ivana");
		assertThrows(ConcurrentModificationException.class, ()->{
			iterator.remove();
		});
	}
	
	
	@Test
	public void twoIterators() {
		SimpleHashtable<String, Integer> table = initTable();
		Iterator<TableEntry<String, Integer>> iterator1 = table.iterator();;
		Iterator<TableEntry<String, Integer>> iterator2 = table.iterator();
		
		assertEquals("Ante",iterator1.next().getKey());
		assertEquals("Ante",iterator2.next().getKey());
		iterator1.remove();
		assertThrows(ConcurrentModificationException.class, ()->{
			iterator2.remove();
		});
		assertThrows(ConcurrentModificationException.class, ()->{
			iterator2.next();
		});
	}
	
	public SimpleHashtable<String, Integer> initTable() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);

		return examMarks;
	}
}
