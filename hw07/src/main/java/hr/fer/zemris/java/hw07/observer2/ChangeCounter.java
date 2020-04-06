package hr.fer.zemris.java.hw07.observer2;

import java.util.Objects;

/**
 * Class represents observer of IntegerStrage.
 * <p>
 * When state of storage is changed by adding new value ChangeCounter observer prints to
 * the standard counter which tracks changes of integer value in storage.
 * <p>
 * <p>
 * Each time value in Integer storage is changed counter
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class ChangeCounter implements IntegerStorageObserver{

	/**
	 * Counter of number times the valueChanged method has been called.
	 * Or the counter of number of times value in IntegerStorage has been changed.
	 */
	private int counter;
	
	/**
	 * Default constructor which initializes counter to 0.
	 */
	public ChangeCounter() {
		super();
		this.counter = 0;
	}


	@Override
	public void valueChanged(IntegerStorageChange change) {
		Objects.requireNonNull(change);
		counter++;
		System.out.printf("Number of value changes since tracking: %d%n" , counter);
	}

}
