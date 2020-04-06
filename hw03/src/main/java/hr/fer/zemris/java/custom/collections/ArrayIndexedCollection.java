package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Implementation of resizable array-backed collection.
 * 
 * @author Filip Hustić
 *
 */
public class ArrayIndexedCollection implements List {

	/**
	 * Current size of collection.
	 */
	private int size;

	/**
	 * An array of object references that are in collection.
	 */
	private Object[] elements;

	/**
	 * Capacity of collection.
	 */
	private int capacity;

	/**
	 * Number of times collection has been modificated by removing and inserting elements,
	 * by reallocating array, by clearing collection . 
	 */
	private long modificationCount;
	
	/**
	 * The number used to set capacity if non is given through constructor.
	 */
	private final static int COLLECTION_INTIAL_CAPACITY = 16;

	

	/**
	 * Default constructor. It sets capacity of collection to 16 and also allocates
	 * array for elements references.
	 */
	public ArrayIndexedCollection() {
		this(COLLECTION_INTIAL_CAPACITY);
	}

	/**
	 * Constructor which takes in initial Capacity.
	 * 
	 * @param initialCapacity capacity set as the number of initial collection
	 *                        references(capacity).
	 * @throws IllegalArgumentException if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException();

		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	/**
	 * Constructor which takes in other Collection which will be added in @this
	 * collection.
	 * 
	 * @param otherCollection collection which elements are copied into newly
	 *                        constructed collection
	 */
	public ArrayIndexedCollection(Collection otherCollection) {
		this(otherCollection, COLLECTION_INTIAL_CAPACITY);
	}

	/**
	 * If the initialCapacity is smaller than the size of the given collection, the
	 * size of the given collection should be used for elements array preallocation.
	 * 
	 * @param otherCollection collection which elements are copied into newly
	 *                        constructed collection
	 * @param initialCapacity capacity set as the number of initial collection
	 *                        references(capacity).
	 * @throws NullPointerException if otherCollection is null
	 */
	public ArrayIndexedCollection(Collection otherCollection, int initialCapacity) {
		if (otherCollection == null)
			throw new NullPointerException();

		this.capacity = initialCapacity > otherCollection.size() ? initialCapacity : otherCollection.size();
		this.elements = new Object[capacity];
		addAll(otherCollection);
	}

	/**
	 * Returns size of collection.
	 * 
	 * @return size of collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds given object to collection.
	 * 
	 * @param value Object to be added into collection.
	 * @throws NullPointerException if given object is null.
	 */
	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();

		if (size == capacity) {
			capacity = capacity * 2;
			elements = Arrays.copyOf(elements, capacity);
			modificationCount++;
		}
		elements[size++] = value;
	}

	/**
	 * Returns Object on given index.
	 * 
	 * @param index of element to be returned.
	 * @return the element at given index.
	 * @throws IndexOutOfBoundsException if index is smaller than 0 or grater than
	 *                                   size-1.
	 */
	public Object get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		return elements[index];
	}

	/**
	 * Empties collection and sets size of collection to 0. Also previous references
	 * in the collection are set to null.
	 */
	@Override
	public void clear() {

		for (int i = 0; i < size; ++i) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Inserts given object at given position. All elements at index equal or
	 * greater of position are shifted one place toward the end. You can not add
	 * element at index higher than the current size of collection.
	 * 
	 * @param value    value to be inserted into collection.
	 * @param position position at witch value will be inserted.
	 * @throws IndexOutOfBoundsException if given position is lower than 0 or if it
	 *                                   is higher than size of collection.
	 * @throws NullPointerException      if given value is null.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		} else if (value == null) {
			throw new NullPointerException();
		}

		if (size == capacity) {
			capacity = capacity * 2;
			elements = Arrays.copyOf(elements, capacity);
		}

		Object putNext = value;
		for (int i = position; i < size + 1; ++i) {
			Object moveNext = elements[i];
			elements[i] = putNext;
			putNext = moveNext;
		}

		++size;
		++modificationCount;
	}

	/**
	 * Method finds first occurrence of given value in the collection.
	 * 
	 * @param value of which index is search in collection.
	 * @return indexOf first occurrence of given object if it exist in collection
	 *         otherwise returns -1. Also if given object is null it returns -1.
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;

		for (int i = 0; i < size; ++i) {
			if (elements[i].equals(value))
				return i;
		}

		return -1;
	}

	/**
	 * Removes element at index from collection. Collection is then reordered so
	 * that all the elements greater than index are shifted toward beginning.
	 * 
	 * @param index of element to be removed.
	 * @throws IndexOutOfBoundsException if index i less than 0 or grater than
	 *                                   size-1.
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException();

		for (int i = index; i < size - 1; ++i) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;
		modificationCount++;
	}

	/**
	 * Removes first occurrence of object if it is in collection and thus returns
	 * true, otherwise returns false.
	 * 
	 * @param value Object to be removed from collection if in it.
	 * @return returns true if collection contains given value and removes one
	 *         occurrence of it. Otherwise it returns false.
	 */
	@Override
	public boolean remove(Object value) {
		if (!contains(value)) {
			return false;
		} else {
			remove(indexOf(value));
			return true;
		}
	}

	/**
	 * Allocates new array with size equals to the size of @this collection, fills
	 * it up with collection content and returns array.
	 * 
	 * @return new array with elements filled from @this collection.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * 
	 * @param value Object to be tested if in the collection.
	 * @return if value is in the collection method returns true, otherwise false.
	 */
	@Override
	public boolean contains(Object value) {
		if (indexOf(value) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @return elements of collection
	 */
	public Object[] getElements() {
		return elements;
	}

	/**
	 * 
	 * @return capacity of collection
	 */
	public int getCapacity() {
		return capacity;
	}
	

	/**
	 * 
	 * @return modificationCount of collection.
	 */
	public long getModificationCount() {
		return modificationCount;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayIndexElementsGetter(this);
	}
	
	/**
	 * Implementation of ElementsGetter which represents iterator through
	 * collection.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class ArrayIndexElementsGetter implements ElementsGetter {

		/**
		 * Index on which ElementsGetter currently points to.
		 */
		private int atIndex;
		/**
		 * Collection through which @this will iterate.
		 */
		private ArrayIndexedCollection collection;
		
		/**
		 * Collection modificationCount at the beginning of iteration.
		 */
		private long savedModificationCount;

		/**
		 * Default constructor
		 * 
		 * @param collection Collection through which @this will iterate.
		 */
		private ArrayIndexElementsGetter(ArrayIndexedCollection collection) {
			this.atIndex = 0;
			this.collection = collection;
			this.savedModificationCount=collection.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if(savedModificationCount!=collection.modificationCount)
				throw new ConcurrentModificationException("Collection has been modified");
			
			return atIndex < collection.size;
		}

		@Override
		public Object getNextElement() {
			if(savedModificationCount!=collection.modificationCount)
				throw new ConcurrentModificationException("Collection has been modified");
		   else if(!hasNextElement())
				throw new NoSuchElementException("There is no more elements in collection.");

			return collection.get(atIndex++);
		}
	}


}
