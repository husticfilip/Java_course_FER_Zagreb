package hr.fer.zemris.java.hw07.observer1;

/**
 * Interface represents observer of integer storage. Every time state of storage
 * changes observer can be notified about it and can do same actions.
 * 
 * @author Filip Hustić
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method which is called from IntegerStorage class when state of it is changed.
	 * <p>
	 * This method represents action which will be done after state change.
	 * <p>
	 * 
	 * @param istorage storage of which @this observer is observer.
	 */
	public void valueChanged(IntegerStorage istorage);

}
