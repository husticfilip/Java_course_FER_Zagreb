package hr.fer.zemris.java.hw07.observer2;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class represents storage of one integer value.
 * 
 * @author Filip Hustić
 *
 */
public class IntegerStorage {

	/**
	 * Value which is stored in storage.
	 */
	private int value;
	/**
	 * List of observers which will be called when integer value is changed.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Default constructor which initializes integer value and list of observers.
	 * 
	 * @param initialValue of integer stored in @this storage.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new CopyOnWriteArrayList<IntegerStorageObserver>();
	}

	/**
	 * Method adds observer to the list of observers.
	 * <p>
	 * If observer is already in the list it is not added again.
	 * <p>
	 * 
	 * @param observer which will be added to the list of observers.
	 * @throws NullPointerException if given observer has null value;
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Method removes given observer from list of observers if one is in it.
	 * 
	 * @param observer to be removed.
	 * @throws NullPointerException if given observer has null value.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		observers.remove(observer);
	}

	/**
	 * Method removes all observers from list of observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * @return integer value stored in @this storage.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Method sets new value in storage.
	 * <p>
	 * New value is set only if it is different from current(old) value.
	 * <p>
	 * <p>
	 * After the value is added all observers are notified about it.
	 * <p>
	 * 
	 * @param value to be set in storage.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}

}
