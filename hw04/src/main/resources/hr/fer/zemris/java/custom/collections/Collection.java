package hr.fer.zemris.java.custom.collections;

/**
 * This class represents general collection of objects.
 * 
 * @author Filip Hustić
 *
 */

public interface Collection {

	/**
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * 
	 * @return number of objects currently stored in collection
	 */
	int size();

	/**
	 * Adds given object to collection.
	 * 
	 * @param value Object to be added into collection.
	 */
	void add(Object value);

	/**
	 * 
	 * @param value Object to be tested if in the collection.
	 * @return if value is in the collection method returns true, otherwise false.
	 */
	boolean contains(Object value);

	/**
	 * 
	 * @param value Object to be removed from collection if in it.
	 * @return returns true if collection contains given value and removes one
	 *         occurance of it. Otherwise it returns false.
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of @this collection, fills
	 * it up with collection content and returns array.
	 * 
	 * @return new array with elements filled from @this collection.
	 * 
	 */
	Object[] toArray();

	/**
	 * Method calls processor.process() on each element of collection and processes
	 * it.
	 * 
	 * @param processor holds method process() which will be used to process
	 *                  elements of collection.
	 */
	default public void forEach(Processor processor) {
		ElementsGetter getter= this.createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Method adds into current collection all elements from given collection.
	 * 
	 * @param other Collection from which elements will be added into @this
	 *              collection.
	 */
	default void addAll(Collection other) {

		/**
		 * Local processor class used to process objects from Collection other int this
		 * method.
		 * 
		 * @author Filip Hustić
		 *
		 */
		class LocalProcessor implements Processor {

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
	void clear();

	/**
	 * This is factory method which returns new ElementsGetter. ElementsGetter can
	 * give information if collection which has made him has more elements and also
	 * can give one element at the time mimicking iterators pattern.
	 * 
	 * @return new istnace of ElementsGetter.
	 */
	public ElementsGetter createElementsGetter();

	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();

		while (getter.hasNextElement()) {
			Object nextElement = getter.getNextElement();
			
			if (tester.test(nextElement))
				this.add(nextElement);
		}

	}

}
