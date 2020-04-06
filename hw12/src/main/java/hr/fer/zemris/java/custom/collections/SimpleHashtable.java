package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represents HashTable which stores key-value pairs. Keys in this map are
 * unique and can not be null, while values can repeat and can be null.
 * 
 * @author Filip Hustić
 *
 * @param <V> values of table entries.
 * @param <K> keys of table entries.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Class that represents one entry in the HashTable.
	 * @author Filip Hustić
	 *
	 * @param <K> values of table entries.
	 * @param <V> keys of table entries.
	 */
	public static class TableEntry<K, V> {

		/**
		 * Key of TableEntry pair.
		 */
		private K key;

		/**
		 * Value of TableEntry pair.
		 */
		private V Value;

		/**
		 * Points to next TableEntry in same slot.
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor of TableEntry which takes in key and value.
		 * 
		 * @param key   of entry.
		 * @param value of entry.
		 * @throws NullPointerException if given key is null.
		 */
		public TableEntry(K key, V value) {
			if (key == null)
				throw new NullPointerException("Key in entry can not be zero");

			this.key = key;
			Value = value;
		}

		/**
		 * 
		 * @return value of entry.
		 */
		public V getValue() {
			return Value;
		}

		/**
		 * Sets value of entry.
		 * 
		 * @param value to be set in entry.
		 */
		public void setValue(V value) {
			Value = value;
		}

		/**
		 * @return key of the entry.
		 */
		public K getKey() {
			return key;
		}

	}

	/**
	 * Array which holds tableEntries.
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Number of TableEntries in @this Map.
	 */
	private int size;

	/**
	 * Default number of slots set if default constructro is called.
	 */
	private final static int DEFAULT_NUMBER_OF_SLOTS = 16;

	/**
	 * Number of times table has been modificated.
	 */
	private long modificationCount;

	/**
	 * Default Constructor which calls other constructor which will initialize array
	 * of TableEntries of size DEFAULT_NUMBER_OF_SLOTS.
	 */
	public SimpleHashtable() {
		this(DEFAULT_NUMBER_OF_SLOTS);
	}

	/**
	 * Constructor which takes in wanted number of slots. It initializes array of
	 * TableEntries to the first value which is potency of number 2 and is grater or
	 * equal to wanted numberOfSlots.
	 * 
	 * @param numberOfSlots wanted number of slots.
	 * @throws IllegalArgumentException if wanted number of slots is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int numberOfSlots) {
		if (numberOfSlots < 1)
			throw new IllegalArgumentException("Number of slots must be greater than 0.");

		int counter = 0;
		int bitsOfNumber = numberOfSlots;
		while (bitsOfNumber > 0) {
			bitsOfNumber = bitsOfNumber >> 1;
			counter++;
		}

		if ((1 << (counter - 1)) != numberOfSlots) {
			numberOfSlots = 1 << (counter);
		}

		table = (TableEntry<K, V>[]) new TableEntry[numberOfSlots];
	}

	/**
	 * Method puts value of given key in map.
	 * <p>
	 * If entry with given key already exists in map value of that entry will be
	 * replaced with value given in this method.
	 * <p>
	 * <p>
	 * If entry with given key does not exist new entry with given key and value is
	 * added on the end of the linkedList of its slot.
	 * <p>
	 * 
	 * <p>
	 * If fillment of table is over 75% new table with double size of old one is
	 * allocated and elements are copied in it
	 * <p>
	 * *
	 * <p>
	 * Table fillment percentage is calculated as
	 * 100*(numberOfslots/numberOfEntries)
	 * <p>
	 * 
	 * @param key   of entry.
	 * @param value to be placed in entry with given key.
	 * @throws NullPointerException if given key is null.
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key can not be null");

		if (((double) (size + 1) / table.length) < 0.75) {
			addToTable(table, key, value);
		} else {
			doubleTheTable();
			addToTable(table, key, value);
		}
	}

	

	/**
	 * Method checks if map contains entry with given key.
	 * 
	 * @param key of entry.
	 * @return true if map contains entry whit given key, false otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		TableEntry<K, V> entry = getSlot(key);

		while (entry != null) {
			if (key.equals(entry.getKey()))
				return true;
			else
				entry = entry.next;
		}

		return false;
	}

	/**
	 * Method returns value of given key if one exists. Else it returns null. If
	 * Given key is null method will also return null.
	 * 
	 * @param key of entry which value will be returned.
	 * @return value of entry with given key. If given entry does not exist method
	 *         returns null.
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		TableEntry<K, V> entry = getSlot(key);
		while (entry != null) {
			if (entry.getKey().equals(key))
				return entry.getValue();
			else
				entry = entry.next;
		}
		return null;
	}

	/**
	 * Method returns size of map.
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * Method checks if there is given value in the table.
	 * <p>
	 * Method iterates through table and returns true when first encounters given
	 * value.
	 * <p>
	 * <p>
	 * If given value is not in the map return value is false.
	 * <p>
	 * 
	 * @param value to be check if in the map.
	 * @return true if value is in the map, false otherwise.
	 */
	public boolean containsValue(Object value) {
		if (value == null)
			return containsNullValue();

		IteratorImpl iterator = new IteratorImpl(modificationCount);
		while (true) {
			try {
				if (iterator.next().getValue().equals(value))
					return true;
			} catch (NoSuchElementException ex) {
				return false;
			}

		}
	}

	/**
	 * Method removes entry from table if exists. If not it does not do anything.
	 * 
	 * @param key of entry which will be removed if exists.
	 */
	public void remove(Object key) {
		if (key == null)
			return;

		int indexOfSlot = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> entry = table[indexOfSlot];

		// if it is head of slot
		if (key.equals(entry.key)) {
			table[indexOfSlot] = entry.next;
			decrementSize_IncrementModification();
			return;
		}

		while (true) {
			if (key.equals(entry.next.getKey())) {
				//we are skipping one that we are removing
				entry.next = entry.next.next;
				decrementSize_IncrementModification();
				break;
			} else if (entry.next == null) {
				break;
			} else {
				entry = entry.next;
			}
		}
	}

	private void decrementSize_IncrementModification() {
		size--;
		modificationCount++;
	}
	
	/**
	 * Method checks if table is empty.
	 * 
	 * @return true if table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method clears table.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = (TableEntry<K, V>[]) new TableEntry[table.length];
		size = 0;
		modificationCount++;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");

		String prefix = "";
		for (int i = 0; i < table.length; ++i) {
			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				sb.append(prefix);
				prefix = ", ";
				sb.append(entry.getKey() + "=" + entry.getValue());
				entry = entry.next;
			}
		}

		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * Method which is called if table is filled over 75% and if it is, new table
	 * with double the slots replaces old table.
	 * <p>
	 * New table has more slots and thus key values of old table could end up in
	 * some other slot than the one of old table. This is because indexOfSlot is
	 * calculated as hashValue(key)%numberOfSlots
	 * <p>
	 */
	private void doubleTheTable() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

		size = 0;// we are adding to table again.

		for (int i = 0; i < table.length; ++i) {
			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				addToTable(newTable, entry.getKey(), entry.getValue());
				entry = entry.next;
			}
		}
		table = newTable;
	}
	
	/**
	 * Helper method.
	 * <p>
	 * Returns new entry with given key-value pair while incrementing table size;
	 * <p>
	 * 
	 * @param key   of new entry
	 * @param value value of new entry.
	 * @return newly initialized entry.
	 */
	private TableEntry<K, V> initNewEntry(K key, V value) {
		modificationCount++;
		size++;
		return new TableEntry<K, V>(key, value);
	}

	/**
	 * Helper method which adds entry with key-value pair into given table. If entry
	 * with given key already exists in table, new value is set to that entry.
	 * 
	 * @param table in which entry will be added, or value will be set to one of its
	 *              entry.
	 * @param key   of entry.
	 * @param value of entry.
	 */
	private void addToTable(TableEntry<K, V>[] table, K key, V value) {
		int indexOfSlot = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> entry = table[indexOfSlot];

		if (entry == null) {
			table[indexOfSlot] = initNewEntry(key, value);
			return;
		}

		while (true) {
			if (key.equals(entry.getKey())) {
				entry.setValue(value);
				return;
			} else if (entry.next == null) {
				break;
			} else {
				entry = entry.next;
			}
		}

		entry.next = initNewEntry(key, value);
	}

	/**
	 * Helper method which checks if table contains null value.
	 * 
	 * @return true if table contains null value, false otherwise.
	 */
	private boolean containsNullValue() {

		IteratorImpl iterator = new IteratorImpl(modificationCount);
		while (true) {
			try {
				if (iterator.next().getValue() == null)
					return true;
			} catch (NoSuchElementException ex) {
				return false;
			}

		}
	}

	/**
	 * Method returns head of slot in which given key belongs.
	 * 
	 * @param key of the entry.
	 * @return head of slot in which given key belongs.
	 */
	private TableEntry<K, V> getSlot(Object key) {
		int indexOfSlot = Math.abs(key.hashCode() % table.length);
		return table[indexOfSlot];
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}

	/**
	 * Class which represents iterator through table made of instances of
	 * TableEntry.
	 * <p>
	 * If table on which iteration is done is has been modificeted since
	 * initialization of iterator, next called method of iterator will result with
	 * exception. Those modifications are adding new entry in table or changing size
	 * of the table. Note that changing value of entry that is already in the table
	 * is legal.
	 * <p>
	 * 
	 * @author Filip Hustić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Index of last slot from which next() returned entry.
		 */
		private int currentSlot;
		/**
		 * Last entry next() returned.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Key of the last element that was removed.
		 */
		private K lastRemovedKey;

		private long iteratorModificationCount;

		/**
		 * Default constructor, it sets currentSlot at -1 because we have not began
		 * iteration yet.
		 */
		public IteratorImpl(long modificationCount) {
			// iteration has not begin
			currentSlot = -1;
			this.iteratorModificationCount = SimpleHashtable.this.modificationCount;
		}

		@Override
		public boolean hasNext() {
			if(iteratorModificationCount!=modificationCount) 
				throw new ConcurrentModificationException();
			
			int helpCurrSlot = currentSlot;
			TableEntry<K, V> helpCurrEntry = currentEntry;

			if (helpCurrEntry == null || helpCurrEntry.next == null) {
				helpCurrSlot++;
				for (; helpCurrSlot < table.length; ++helpCurrSlot) {
					if ((helpCurrEntry = table[helpCurrSlot]) != null)
						return true;
				}
				
			} else {
				return true;
			}

			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			if(iteratorModificationCount!=modificationCount) 
				throw new ConcurrentModificationException();
			
			if (currentEntry == null || currentEntry.next == null) {
				currentSlot++;
				for (; currentSlot < table.length; ++currentSlot) {
					if ((currentEntry = table[currentSlot]) != null)
						return currentEntry;
				}
				
			} else {
				currentEntry = currentEntry.next;
				return currentEntry;
			}
			// iterator has iterated to the end.
			throw new NoSuchElementException("There is no next value in table");
		}

		@Override
		public void remove() {
			if(iteratorModificationCount!=modificationCount) 
				throw new ConcurrentModificationException();
			
			if (currentEntry == null || (lastRemovedKey != null && lastRemovedKey.equals(currentEntry.getKey())))
				throw new IllegalStateException(
						"Next method has not yet been called, or the remove method has already been called after the last call to the next method");

			SimpleHashtable.this.remove(currentEntry.getKey());
			lastRemovedKey = currentEntry.getKey();
			iteratorModificationCount++;
		}

	}

}
