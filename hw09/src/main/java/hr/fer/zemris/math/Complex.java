package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents complex number with its real and imaginary part.
 * 
 * @author Filip Hustić
 *
 */
public class Complex {

	/**
	 * ComplexNumber: 0
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * ComplexNumber: 1
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * ComplexNumber: -1
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * ComplexNumber: i
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * ComplexNumber: -1
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of complex number.
	 */
	private final double real;

	/**
	 * Imaginary part of complex number.
	 */
	private final double imaginary;

	/**
	 * Default constructor which initializes complex number to number 0;
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor which takes in real and imaginary part of complex number.
	 * 
	 * @param real part of complex number.
	 * @param imag imaginry part of complex number.
	 */
	public Complex(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Method calculates and returns module of complex number.
	 * 
	 * @return module of complex number.
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Method returns new Complex number which is product of multiplication of @this
	 * complex number and given complex number.
	 * 
	 * @param c complex number to be multiplied with @this complex number.
	 * @return product of multiplication.
	 * @throws NullPointerException if given complex number is null.
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(real * c.real - imaginary * c.imaginary, imaginary * c.real + real * c.imaginary);
	}

	/**
	 * Method divides @this complex number with given complex number and returns
	 * result in form of new complex number.
	 * 
	 * @param c complex number to be divided from @this complex number.
	 * @return quotient of @this complex number divided from given complex number.
	 * @throws IllegalArgumentException if division is done by 0.
	 * @throws NullPointerException     if given complex number is null.
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);
		if (c.getReal() == 0 && c.getImaginary() == 0)
			throw new IllegalArgumentException("Given complex number is 0.");

		Complex numerator = this.multiply(new Complex(c.getReal(), -c.getImaginary()));
		Complex denominator = c.multiply(new Complex(c.getReal(), -c.getImaginary()));

		return new Complex(numerator.getReal() / denominator.getReal(),
				numerator.getImaginary() / denominator.getReal());
	}

	/**
	 * Method adds given complex number with @this complex number.
	 * 
	 * @param c complex number to be added to @this complex number.
	 * @return sum of addition as new complex number.
	 * @throws NullPointerException if given complex number is null.
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Method subs given complex number from @this complex number.
	 * 
	 * @param c complex number to be subed from @this complex number.
	 * @return difference of @this complex number and given complex number.
	 * @throws NullPointerException if given complex number is null.
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Method returns negated version of @this complex number.
	 * 
	 * @return negated version of @this complex number.
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Method calculates n power of @this complex number.
	 * 
	 * @param n power on which calculation will be done.
	 * @return power n of @this complex number and returns it as new complex number.
	 * @throws IllegalArgumentException if n is less than 0.
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be grater or equal than 0.");

		if (n == 0) {
			return new Complex(1, 0);
		}

		double r = module();
		double angle = getAngle();
		return new Complex(Math.pow(r, n) * Math.cos(n * angle), Math.pow(r, n) * Math.sin(n * angle));
	}

	/**
	 * Method calculates and returns nth root of @this complex number.
	 * 
	 * @param n nth of root on which calulation will be done.
	 * @return nth root n @this complex number and returns it as new complex number.
	 * @throws IllegalArgumentException if n is less than 1.
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be grater than zero.");
		double magnitude = Math.pow(module(), (double) 1 / n);
		double angle = getAngle();

		List<Complex> roots = new ArrayList<Complex>();

		for (int k = 0; k < n; ++k) {
			roots.add(new Complex(magnitude * Math.cos((angle + 2 * Math.PI * k) / n),
					magnitude * Math.sin((angle + 2 * Math.PI * k) / n)));
		}
		return roots;
	}

	/**
	 * @return newly initialized copy of @this complex number.
	 */
	public Complex copyOf() {
		return new Complex(getReal(), getImaginary());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%.1f", real));
		
		if(imaginary>=0) {
			builder.append(String.format("+i%.1f", imaginary));
		}else {
			builder.append(String.format("-i%.1f", Math.abs(imaginary)));
		}
		
		return builder.toString();
	}

	/**
	 * Method calculates and returns angle of @this complex number Note that for
	 * number 0 function returns 0;
	 * 
	 * @return angle of @this complex number.
	 */
	private double getAngle() {

		// is angle on the axes
		if (imaginary == 0) {
			if (real >= 0)
				return 0;
			else
				return Math.PI;

		}
		if (real == 0) {
			if (imaginary > 0)
				return Math.PI * 1 / 2;
			else
				return Math.PI * 3 / 2;
		}

		double angle = Math.atan(Math.abs(imaginary) / Math.abs(real));
		// if not on axes, in which quadrant is it determines actual angle.
		if (real > 0 && imaginary > 0) {
			return angle;
		} else if (real < 0 && imaginary > 0) {
			return Math.PI - angle;
		} else if (real < 0 && imaginary < 0) {
			return Math.PI + angle;
		} else {
			return Math.PI * 2 - angle;
		}

	}

	/**
	 * 
	 * @return real part of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * 
	 * @return imaginary part of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

}
