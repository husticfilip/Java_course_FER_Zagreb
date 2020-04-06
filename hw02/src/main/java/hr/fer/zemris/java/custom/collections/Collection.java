package hr.fer.zemris.java.custom.collections;

/**
 * This class represents general collection of objects.
 * 
 * @author Filip Hustić
 *
 */

public class Collection {

	/**
	 * Protected default constructor of class Collection.
	 */
	protected Collection() {

	}

	/**
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * 
	 * @return number of objects currently stored in collection. Here it returns 0
	 *         so it must be override.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds given object to collection.
	 * 
	 * @param value Object to be added into collection. Here it does nothing so it
	 *              must be override.
	 */
	public void add(Object value) {

	}

	/**
	 * 
	 * @param value Object to be tested if in the collection.
	 * @return if value is in the collection method returns true, otherwise false.
	 *         Here it always returns false so it must be override.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * 
	 * @param value Object to be removed from collection if in it.
	 * @return returns true if collection contains given value and removes one
	 *         occurance of it. Otherwise it returns false. Here it always returns
	 *         false so it must be override.
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of @this collection, fills
	 * it up with collection content and returns array.
	 * 
	 * @return new array with elements filled from @this collection.
	 * @throws UnsupportedOperationException so it must be overide.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process() on each element of collection and processes
	 * it.
	 * 
	 * @param processor holds method process() which will be used to process
	 *                  elements of collection. Here it is implemented as empty body
	 *                  so it must be override.
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into current collection all elements from given collection.
	 * 
	 * @param other Collection from which elements will be added into @this
	 *              collection.
	 */
	public void addAll(Collection other) {

		/**
		 * Local processor class used to process objects from Collection other int this
		 * method.
		 * 
		 * @author Filip Hustić
		 *
		 */
		class LocalProcessor extends Processor {

			/**
			 * Method processes given value.
			 * @param value object to be processed.
			 */
			@Override
			public void process(Object value) {
				add(value);
				// TODO sta se ovdje dogada
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Removes all elements from collection. Here it is implemented as empty method
	 * so it must be override.
	 */
	public void clear() {

	}

}
