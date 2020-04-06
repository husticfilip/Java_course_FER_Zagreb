package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Implementation of linked list-backed collection of the objects.
 * 
 * @author Filip Hustić
 *
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * Represents one node in the list with references to next and previous nodes
	 * and with additional reference to assigned value.
	 * 
	 * @param next     points to the next node in collection.
	 * @param previous points to the previous node in collection.
	 * @author Filip Hustić
	 *
	 */
	private static class ListNode {
		public ListNode next;
		public ListNode previous;
		public Object value;

		/**
		 * 
		 * @return next node in Collection.
		 */
		public ListNode getNext() {
			return next;
		}

		/**
		 * 
		 * @return previous node in Collection.
		 */
		public ListNode getPrevious() {
			return previous;
		}

		/**
		 * 
		 * @return value of the node.
		 */
		public Object getValue() {
			return value;
		}
		
	}
	
	

	/**
	 * Size of the collection. Number of nodes in collection.
	 */
	private int size;

	/**
	 * Reference to the first node in the collection.
	 */
	private ListNode first;

	/**
	 * Reference to the last node in collection.
	 */
	private ListNode last;
	
	/**
	 * Number of times collection has been modificated by removing elements, inserting elements, 
	 * adding elements or by clearing collection . 
	 */
	private long modificationCount;

	/**
	 * Default constructor. It sets first and last node to null.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
	}

	/**
	 * Constructor which copies given collection to newly constructed collection.
	 * 
	 * @param otherCollection collection to be copied in new collection.
	 * @throws NullPointerException thrown if otherCollection is null.
	 */
	public LinkedListIndexedCollection(Collection otherCollection) {
		if (otherCollection == null)
			throw new NullPointerException();

		addAll(otherCollection);
	}

	/**
	 * 
	 * @return size of collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 
	 * @return first node in the collection.
	 */
	public ListNode getFirst() {
		return first;
	}

	/**
	 * 
	 * @return last node in the collection.
	 */
	public ListNode getLast() {
		return last;
	}

	/**
	 * Method adds object to the end of collection. If collection is empty first
	 * node is initialized and holds @param value also in that case reference of the
	 * last node is the same as the reference of the first. In every other case node
	 * is added to the end of collection such that previous last node points to new
	 * node(next) and also there is back connection in the new node that points to
	 * the previous last node(last).
	 * 
	 * @param value object that is put as the value of new node.
	 * @throws NullPointerException if given object is null.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		if (first == null) {
			first = new ListNode();
			first.value = value;
			last = first;
		} else {
			last.next = new ListNode();
			last.next.value = value;
			last.next.previous = last;
			last = last.next;
		}

		++size;
		++modificationCount;
	}

	/**
	 * Method returns object at given index.
	 * @param index of the searched value in the collection.
	 * @return value of node at the given index.
	 * @throws IndexOutOfBoundsException if index is less tha 0 or grater than
	 *                                   (size-1).
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException();

		// helpNode helps to find node with given index
		ListNode helpNode;
		if (index < (size / 2)) {
			helpNode = first;
			for (int i = 0; i < index; ++i) {
				helpNode = helpNode.next;
			}
		} else {
			int distanceFromLast = size - 1 - index;
			helpNode = last;
			for (int i = 0; i < distanceFromLast; ++i) {
				helpNode = helpNode.previous;
			}
		}
		// in the end helpNode is the node at given index
		return helpNode.value;
	}

	/**
	 * Removes all elements from collection in the way to put first node to null and
	 * last node to the null
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
		++modificationCount;
	}

	/**
	 * Method inserts node with given value in the collection in the way that every
	 * node at the indexes equal or greater than position are shifted by one place
	 * towards the end.
	 * 
	 * @param value    object to be inserter into collection.
	 * @param position of node which will hold given value.
	 * @throws IndexOutOfBoundsException if position is less than 0 or grater than
	 *                                   size of collection.
	 *@throws NullPointerException if given value is null
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size)
			throw new IndexOutOfBoundsException();
		else if(value == null)
			throw new NullPointerException();

		if (position == 0) {
			ListNode nodeToInsert = new ListNode();
			nodeToInsert.value = value;
			nodeToInsert.next = first;
			first.previous = nodeToInsert;
			first = nodeToInsert;
		} else if (position == size) {
			add(value);
			// in add function we are already adding size++
			size--;
		} else {
			// helpNode helps to find node with given postion so
			// new node is added between him and previous one
			ListNode helpNode;
			if (position < (size / 2)) {
				helpNode = first;
				for (int i = 0; i < position; ++i) {
					helpNode = helpNode.next;
				}
			} else {
				int distanceFromLast = size - 1 - position;
				helpNode = last;
				for (int i = 0; i < distanceFromLast; ++i) {
					helpNode = helpNode.previous;
				}
			}
			ListNode nodeToInsert = new ListNode();
			nodeToInsert.value = value;
			nodeToInsert.previous = helpNode.previous;
			nodeToInsert.next = helpNode;
			helpNode.previous.next = nodeToInsert;
			helpNode.previous = nodeToInsert;
		}
		size++;
		modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value which is searched through collection.
	 * @return index of searched object or -1 if object does not exist in
	 *         collection.
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;

		// node which helps us iterating through list
		ListNode helpNode = first;

		for (int i = 0; i < size; ++i) {
			if (helpNode.value.equals(value)) {
				return i;
			} else {
				helpNode = helpNode.next;
			}
		}

		return -1;
	}

	/**
	 * Method removes node at given index in a way to shift all nodes greater than
	 * given index one position toward the beginning. If size of collection is equal
	 * to 1 after remove there is no nodes in collection, collection is cleared.
	 * 
	 * @param index of node to be removed.
	 */
	public void remove(int index) {
		if (index < 0 || index > (size - 1))
			throw new IndexOutOfBoundsException();

		if (size == 1) {
			clear();
		} else if (index == 0) {
			first = first.next;
			first.previous = null;
			size--;
		} else if (index == (size - 1)) {
			last = last.previous;
			last.next = null;
			size--;
		} else {
			// helpNode helps to find node with given postion so
			// new node is added between him and previous one
			ListNode helpNode;
			if (index < (size / 2)) {
				helpNode = first;
				for (int i = 0; i < index; ++i) {
					helpNode = helpNode.next;
				}
			} else {
				int distanceFromLast = size - 1 - index;
				helpNode = last;
				for (int i = 0; i < distanceFromLast; ++i) {
					helpNode = helpNode.previous;
				}
			}
			helpNode.previous.next = helpNode.next;
			helpNode.next.previous = helpNode.previous;
			size--;
		}
		modificationCount++;
	}

	/**
	 * Method checks if collection contains given value
	 * @param value Object to be tested if in the collection.
	 * @return if value is in the collection method returns true, otherwise false.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) == -1 ? false : true;
	}

	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes
	 *	first occurrence of it, otherwise returns false.
	 * @param value Object to be removed from collection if in it.
	 * @return returns true if collection contains given value and removes first
	 *         occurrence of it. Otherwise it returns false.
	 * @throws N
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);

		if (index == -1) {
			return false;
		} else {
			remove(index);
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
		Object[] array = new Object[size];

		ListNode helpNode = first;
		for (int i = 0; i < size; ++i) {
			array[i] = helpNode.value;
			helpNode = helpNode.next;
		}

		return array;
	}

	/**
	 * 
	 * @return collection modificationCount
	 */
	public long getModificationCount() {
		return modificationCount;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexElementsGetter(this);
	}
	
	/**
	 * Implementation of ElementsGetter which represents iterator through
	 * collection. Iteration is done in the way so variable node holds 
	 * reference to the node to be getNext, and after it gets it node.next is
	 * set to node.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class LinkedListIndexElementsGetter implements ElementsGetter {

	
		/**
		 * Node at which @this ElementsGetter is currently on.
		 */
		private ListNode node;
		/**
		 * Collection through which @this will iterate.
		 */
		private LinkedListIndexedCollection collection;
		
		/**
		 * Collection modificationCount at the beginning of iteration.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor which sets first node of collection to current node of @this ElementsGetter.
		 * @param first
		 */
		private LinkedListIndexElementsGetter(LinkedListIndexedCollection collection) {
			this.collection=collection;
			this.node=collection.getFirst();
			this.savedModificationCount=collection.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if(savedModificationCount!=collection.modificationCount)
				throw new ConcurrentModificationException("Collection has been modified");
			
			if(node==null) return false;
		
			return true;
		}

		@Override
		public Object getNextElement() {
			if(savedModificationCount!=collection.modificationCount)
				throw new ConcurrentModificationException("Collection has been modified");
			else if (!hasNextElement())
				throw new NoSuchElementException("There is no more elements in collection.");
			
			Object toReturn=node.value;
			node=node.next;
			return toReturn;
		}
	}

}
