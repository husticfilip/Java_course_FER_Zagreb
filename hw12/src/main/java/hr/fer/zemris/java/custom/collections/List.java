package hr.fer.zemris.java.custom.collections;

/**
 * Interface class which contains methods:get,insert,indexOf,remove. Those
 * methods will be used on the list collection.
 * @author Filip Hustić
 *
 */
public interface List<T> extends Collection<T> {

	/**
	 * Returns object at the given index if one exist.
	 * @param index of object to be returned. 
	 * @return object at given index.
	 * @throws IndexOutOfBoundsException if element at given index does not exist.
	 */
	T get(int index);
	
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
	void insert(T value, int position);
	
	/**
	 * Method finds first occurrence of given value in the collection.
	 * 
	 * @param value of which index is search in collection.
	 * @return indexOf first occurrence of given object if it exist in collection
	 *         otherwise returns -1. Also if given object is null it returns -1.
	 */
	int indexOf(T value);
	
	/**
	 * Removes element at index from collection. Collection is then reordered so
	 * that all the elements greater than index are shifted toward beginning.
	 * 
	 * @param index of element to be removed.
	 * @throws IndexOutOfBoundsException if index i less than 0 or grater than
	 *                                   size-1.
	 */
	void remove(int index);

}
