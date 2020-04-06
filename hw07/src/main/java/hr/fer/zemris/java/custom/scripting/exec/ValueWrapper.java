package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class which represents wrapper of Object value.
 * 
 * @author Filip Hustić
 *
 */
public class ValueWrapper {

	/**
	 * Value wrapped in this instance.
	 */
	public Object value;

	/**
	 * Default constructor which takes in object value.
	 * 
	 * @param value of object wrapped.
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Method adds value of given object to value wrapped in wrapper.
	 * <p>
	 * Calculation is done using {@link #calculate} method.
	 * <p>
	 * 
	 * @param incValue value to be added to value wrapped in wrapper.
	 */
	public void add(Object incValue) {
		value = calculate(value, incValue, (v, i) -> v.doubleValue() + i.doubleValue());
	}

	/**
	 * Method subtracts value of given object from value wrapped in wrapper.
	 * <p>
	 * Calculation is done using {@link #calculate} method.
	 * <p>
	 * 
	 * @param decValue value to be subtracted from value wrapped in wrapper.
	 */
	public void subtract(Object decValue) {
		value = calculate(value, decValue, (v, d) -> v.doubleValue() - d.doubleValue());
	}

	/**
	 * Method multiplies value stored in wrapper with given value.
	 * 
	 * <p>
	 * Calculation is done using {@link #calculate} method.
	 * <p>
	 * 
	 * @param mulValue with which value in wrapper will be added.
	 */
	public void multiply(Object mulValue) {
		value = calculate(value, mulValue, (v, m) -> v.doubleValue() * m.doubleValue());
	}

	/**
	 * Method divides value stored in wrapper with given value.
	 * <p>
	 * Calculation is done using {@link #calculate} method.
	 * <p>
	 * 
	 * @param divValue value with which value stored in wrapper will be devided.
	 */
	public void divide(Object divValue) {
		value = calculate(value, divValue, new BiFunction<Number, Number, Double>() {

			@Override
			public Double apply(Number t, Number d) {
				if (d.doubleValue() == 0.0) {
					throw new IllegalArgumentException("Divident must be diferent than 0.");
				}
				return t.doubleValue() / d.doubleValue();
			}
		});
	}

	/**
	 * Method compares value stored in wrapper with given value.
	 * <p>
	 * If value of wrapper or given value is not Integer, Double or String
	 * representation of thoes two exception is being thrown-
	 * <p>
	 * 
	 * @param withValue value with which comparison will be done.
	 * @return int value greater than 0 if value of wrapper is greater than given
	 *         value, int value less than 0 if value of wrapper is less than given
	 *         value, or 0 if values are the same.
	 */
	public int numCompare(Object withValue) {
		Number firstNumber = getNumericValue(value);
		Number secondNumber = getNumericValue(withValue);

		if ((firstNumber instanceof Double) || (secondNumber instanceof Double)) {
			return Double.compare(firstNumber.doubleValue(), secondNumber.doubleValue());
		} else {
			return Integer.compare(firstNumber.intValue(), secondNumber.intValue());
		}

	}

	/**
	 * Method which takes in BiFunction and applies action on given objects.
	 * <p>
	 * Both objects must be eather Integer,Double or String representation of those
	 * two. Otherwise exception is thrown.
	 * <p>
	 * 
	 * @param first    value of first object.
	 * @param second   value of second object.
	 * @param function BiFunction.
	 * @return number produced by BiFunction which takes in values given through
	 *         this method.
	 * @throws RuntimeException if eather value is not Integer,Double or String
	 *                          representation of those two.
	 */
	private Number calculate(Object first, Object second, BiFunction<Number, Number, Double> function) {
		Number firsNumber = getNumericValue(first);
		Number secondNumber = getNumericValue(second);
		Double result = function.apply(firsNumber, secondNumber);

		if ((firsNumber instanceof Double) || (secondNumber instanceof Double)) {
			return result;
		} else {
			return Integer.valueOf((int) Math.round(result));
		}
	}

	/**
	 * Method returns Number representation of given object.
	 * <p>
	 * If object is already Double or Integer type, it is return without
	 * modification just casting in Number.
	 * <p>
	 * <p>
	 * If object is String type and number can be parsed form that string, that
	 * number is returned in form of Double or Integer which depends on string.
	 * <p>
	 * 
	 * @param value to be represented as Number.
	 * @return Number representation of given object.
	 * @throws RuntimeException if eather value is not Integer,Double or String
	 *                          representation of those two.
	 */
	private Number getNumericValue(Object value) {
		if (value == null) {
			return Integer.valueOf(0);
		}

		if (!(value instanceof Integer) && !(value instanceof Double) && !(value instanceof String)) {
			throw new RuntimeException("Value was not of instance Integer, Double, String or null value.");
		}

		if (value instanceof String) {
			String stringValue = (String) value;

			try {
				if (stringValue.contains(".") || stringValue.toLowerCase().contains("e")) {
					return Double.parseDouble(stringValue);
				} else {
					return Integer.parseInt(stringValue);
				}

			} catch (NumberFormatException ex) {
				throw new RuntimeException("Could not parse to Double or Integer");
			}

		} else {
			return (Number) value;
		}
	}

	/**
	 * 
	 * @return value wrapped in wrapper.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value to be set in wrapper.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
