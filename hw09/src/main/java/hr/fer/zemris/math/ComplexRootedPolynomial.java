package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class represents rooted polynomial of complex function.
 * <p>
 * Function is given by: f(z) = z0 * (z - z1) * (z - z2)*...(z - zn) <br>
 * Where z0 is complex constant and z1..zn are zeros(roots) of polynomial.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Complex constant.
	 */
	private Complex constant;
	/**
	 * Zeros(roots) of polynomial.
	 */
	private List<Complex> roots;

	/**
	 * Constructor which takes in constant and roots of polynomial.
	 * <p>
	 * Function is given by: f(z) = z0 * (z - z1) * (z - z2)*...(z - zn) <br>
	 * Where z0 is complex constant and z1..zn are zeros(roots) of polynomial.
	 * <p>
	 * 
	 * @param constant of polynomial.
	 * @param roots    of polynomial.
	 * @throws NullPointerException if constant or roots are null.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		Objects.requireNonNull(constant);
		Objects.requireNonNull(roots);
		this.constant = constant;
		this.roots = new ArrayList<Complex>(Arrays.asList(roots));
	}

	/**
	 * Method calculates value of @this polynomial for given Complex number z.
	 * <p>
	 * Function is given by: f(z) = z0 * (z - z1) * (z - z2)*...(z - zn)
	 * <p>
	 * 
	 * @param z complex number for which value of polynomial will be calculated.
	 * @return value of calculated polynomial.
	 */
	public Complex apply(Complex z) {
		Complex result = constant.copyOf();

		for (Complex root : roots) {
			result = z.sub(root).multiply(result);
		}

		return result;
	}

	/**
	 * Method converts this rooted polynomial into polynomial.
	 * <p>
	 * Conversion is done: <br>
	 * z0(z-z1)*(z-z2)..*(z-zn) = z^n * cn + z^n-1 * cn-1 ... * c0
	 * <p>
	 * 
	 * @return polynomial interpretation of this rooted polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);

		for (Complex root : roots) {
			result = result.multiply(new ComplexPolynomial(
					new Complex[] { new Complex(-root.getReal(), -root.getImaginary()), Complex.ONE }));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(constant);
		builder.append(")");

		for (Complex root : roots) {
			builder.append(" * (z-(");
			builder.append(root);
			builder.append(")");
		}

		return builder.toString();
	}

	/**
	 * Method returns index of closest root of complex rooted polynomial to the
	 * given Complex number z which is within given treshold.<br>
	 * If there are no roots within given treshold -1 is returned.<br>
	 * Roots are indexed the way they are given through constructor.
	 * 
	 * @param z        Complex number for which closest root will be found.
	 * @param treshold for finding closest root.
	 * @return closest root to the given complex number z. If there are no roots in
	 *         distance less than treshold of Complex number z -1 is returned.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double lowestDistance = treshold;

		for (int i = 0; i < roots.size(); ++i) {
			double distance = roots.get(i).sub(z).module();
			if (distance < lowestDistance) {
				lowestDistance = distance;
				index = i;
			}
		}

		return index;
	}

}
