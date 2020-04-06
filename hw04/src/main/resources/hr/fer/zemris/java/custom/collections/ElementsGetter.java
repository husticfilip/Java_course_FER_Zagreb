package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Interface which represents iterator of collection.
 * It goes through collection and returns elements in the
 * order in which they were put in collection. If collection in the
 * process of iteration has been modified by insertion or removal of elements
 * or by reallocating or by clearing, on next method call there will be thrown
 * exception 
 * @author Filip Hustić
 *
 */
public interface ElementsGetter {
   /**
    * Method which determines if collection has more elements which
    * have not been given via method getNextElement.
    * @return true if iteration has not come to the end of the collection,
    * 		  false otherwise.
    * @throws ConcurrentModificationException if collection has been modified 
    * 		 during the iteration through it.
    */
	boolean hasNextElement();
	/**
	 * Method which returns one element by one call. It returns them
	 * in the order they were put in the collection originaly.
	 * @return next element in line if there is one.
	 * @throws NoSuchElementException if all elements have been 
	 * returned.
	 * @throws ConcurrentModificationException if collection has been modified 
     * 		 during the iteration through it.
	 */
	Object getNextElement();

	/**
	 * This is default method of interface processRemaining. It calls 
	 * processors method process on each element that has not been 
	 * iterated by @this instance of ElementsGetter.
	 * @param p
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
