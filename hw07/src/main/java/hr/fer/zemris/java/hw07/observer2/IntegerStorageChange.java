package hr.fer.zemris.java.hw07.observer2;

/**
 * Class which encapsulates integerStorage and holds integer value before change
 * of integerStorage state and integer value after change.
 * 
 * @author Filip Hustić
 *
 */
public class IntegerStorageChange {

	/**
	 * Storage in which change was made.
	 */
	private IntegerStorage integerStorage;
	/**
	 * Integer value which was stored in integerStorage before change.
	 */
	private int valueBeforeChange;
	/**
	 * Integer value which was stored in integerStorage after change.
	 */
	private int valueAfterChange;

	/**
	 * Default constructor which takes in integerStorage, integer value which was
	 * stored in integerStorage before change and Integer value which was stored in
	 * integerStorage after change.
	 * 
	 * @param integerStorage    storage of integer value;
	 * @param valueBeforeChange value before change in storage.
	 * @param valueAfterChange  value after change in storage.
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int valueBeforeChange, int valueAfterChange) {
		super();
		this.integerStorage = integerStorage;
		this.valueBeforeChange = valueBeforeChange;
		this.valueAfterChange = valueAfterChange;
	}

	/**
	 * 
	 * @return storage.
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * 
	 * @return value before change.
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}

	/**
	 * 
	 * @return value after change.
	 */
	public int getValueAfterChange() {
		return valueAfterChange;
	}
	
	

}
