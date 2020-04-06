package hr.fer.zemris.java.hw07.observer1;

import java.util.Objects;

/**
 * Class represents observer of IntegerStrage.
 * <p>
 * When state of storage is changed by adding new value SquareValue observer prints to
 * the standard output new value and squared value of it.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		Objects.requireNonNull(istorage);
		int newValue = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", newValue, newValue * newValue);
	}

}
