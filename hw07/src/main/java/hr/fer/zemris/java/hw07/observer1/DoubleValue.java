package hr.fer.zemris.java.hw07.observer1;

import java.util.Objects;

/**
 * Class represents objects which will print to standard output
 * double(multiplied by 2) value of newly set value of IntegerStorage.
 * 
 * @author Filip Hustić
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	private int callsLeft;

	/**
	 * Default constructor which initializes callsLeft.
	 * 
	 * @param callsLeft number of calls in which there will be output in
	 *                  valueChanged method.
	 */
	public DoubleValue(int callsLeft) {
		super();
		this.callsLeft = callsLeft;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Method prints out double value of new value in storage if there is calls
	 * left.
	 * <p>
	 * <p>
	 * Number of calls is set through constructor of @this class.
	 * <p>
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		Objects.requireNonNull(istorage);

		if (callsLeft != 0) {
			System.out.printf("Double value: %d%n", istorage.getValue() * 2);
			--callsLeft;
		}else {
			istorage.removeObserver(this);
		}
	}

}
