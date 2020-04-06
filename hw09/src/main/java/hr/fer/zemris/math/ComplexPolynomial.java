package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class represents complex polynomial.
 * <p>
 * Polynomial is given by: <br>
 * f(z) = z^n * cn + z^(n-1) * cn-1 + ... c0; <br>
 * <br>
 * 
 * c0-cn are complex coefficients of polynomial which are given through
 * constructor.<br>
 * 
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class ComplexPolynomial {

	/**
	 * Coefficients of polynomial.
	 */
	private Complex[] coefficients;

	/**
	 * Constructor which takes in complex coefficients of polynomial.
	 * 
	 * <p>
	 * Polynomial is given by: <br>
	 * f(z) = z^n * cn + z^(n-1) * cn-1 + ... c0; <br>
	 * Where cn-c0 are coefficients.
	 * 
	 * @param factors complex coefficients of polynomial.
	 * @throws NullPointerException if factors are null.
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);
		coefficients = Arrays.copyOf(factors, LastNonZero(factors));
	}

	/**
	 * Method returns order of polynomial.<br>
	 * eq. z^2 * c2 + z * c1 + co is polynomial of order 2.
	 * 
	 * @return order of polynomial.
	 */
	public short order() {
		return (short) (coefficients.length - 1);
	}

	/**
	 * Method multiplies this polynomial with given polynomial and returns result in
	 * form of new polynomial.
	 * 
	 * @param p polynomial with which this will be multiplied.
	 * @return result of multiplication in form of new ComplexPolynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[order() + p.order() + 1];

		// init factors
		for (int i = 0; i < newFactors.length; ++i) {
			newFactors[i] = Complex.ZERO;
		}

		for (int i = 0, thisOrder = order(); i <= thisOrder; ++i) {
			for (int j = 0, pOrder = p.order(); j <= pOrder; ++j) {
				Complex multiplyResult = getCoefficitent(i).multiply(p.getCoefficitent(j));
				newFactors[i + j] = newFactors[i + j].add(multiplyResult);
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Method derives this polynomial and returns result in form of new polynomial.
	 * 
	 * @return derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		if (order() == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}

		Complex[] newFactors = new Complex[order()];

		for (int i = 0; i < newFactors.length; ++i) {
			newFactors[i] = getCoefficitent(i + 1).multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Method calculates value of this polynomial for given Complex number z and
	 * returns it in form of new complex number.
	 * 
	 * <p>
	 * Polynomial is given by: <br>
	 * f(z) = z^n * cn + z^(n-1) * cn-1 + ... c0; <br>
	 * <br>
	 * 
	 * @param z for which value of polynomial will be calculated.
	 * @return value of polynomial in form of new Complex number.
	 */
	public Complex apply(Complex z) {
		// free coeff
		Complex result = getCoefficitent(0).copyOf();

		for (int n = 1, order = order(); n <= order; ++n) {
			Complex term = z.power(n).multiply(getCoefficitent(n));
			result = result.add(term);
		}

		return result;
	}

	/**
	 * Method returns coefficient attached to of given potency.
	 * 
	 * @param potency of coefficient.
	 * @return coefficient attached to of given potency.
	 * @throws IndexOutOfBoundsException if potency is less than 0, or if potency is
	 *                                   higher than order of polynomial.
	 */
	public Complex getCoefficitent(int potency) {
		return coefficients[potency];
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int n = order(); n > 0; --n) {
			builder.append("(");
			builder.append(getCoefficitent(n));
			builder.append(")");
			builder.append("z^");
			builder.append(n);
			builder.append(" + ");
		}
		
		builder.append("(");
		builder.append(getCoefficitent(0));
		builder.append(")");

		return builder.toString();
	}

	/**
	 * Helper method which determines which is last index of factor that is not
	 * zero. If all factors are zero, index 1 is returned, which denotes that
	 * polynomial is constructed of one coefficient which is zero..
	 * 
	 * @param factors
	 * @return index of last factor that is not zero, or 1 if all factors are zeros.
	 */
	private int LastNonZero(Complex[] factors) {

		for (int i = factors.length - 1; i > 0; --i) {
			if (factors[i].getReal() != 0 || factors[i].getImaginary() != 0) {
				return i + 1;
			}
		}
		return 1;
	}

}
