package hr.fer.zemris.java.hw02;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that represents complex number with its real and imaginary part. Note
 * that either real or imaginary part can be 0 thus do not exist.
 * 
 * @author Filip Hustić
 *
 */
public class ComplexNumber {

	/**
	 * Real part of complex number.
	 */
	private final double real;
	/**
	 * Imaginary part of complex number.
	 */
	private final double imaginary;

	/**
	 * Constructor which initialize real and imaginary part of complex number;
	 * 
	 * @param real      given real part of complex number.
	 * @param imaginary given imaginary part of complex number.
	 * @throws NullPointerException if either real or imaginary part is null.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Factory method that returns new complex number with only real component.
	 * 
	 * @param real given real part of number.
	 * @return new complex number with only real component.
	 */
	public static ComplexNumber formReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Factory method that returns new complex number with only imaginary component.
	 * 
	 * @param imaginary given imaginary part of number.
	 * @return new complex number with only imaginary component.
	 */
	public static ComplexNumber formImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Method determines real and imaginary part of complex number based on its
	 * magnitude and angle.It preforms for any given angle.
	 * 
	 * @param magnitude @param angle @return @throws
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0)
			throw new IllegalArgumentException("Magnitude must be positive number");

		// transform in are from 0 to 2PI
		angle = angleTransformBetween0and2Pi(angle);

		// does number have just real or just imaginary part
		if (angle == 0 || angle == Math.PI * 2) {
			return new ComplexNumber(magnitude, 0);
		} else if (angle == Math.PI / 2) {
			return new ComplexNumber(0, magnitude);
		} else if (angle == Math.PI) {
			return new ComplexNumber(-magnitude, 0);
		} else if (angle == Math.PI * 3 / 2) {
			return new ComplexNumber(0, -magnitude);
		}

		// real and imaginary part
		// what sign will what part hold
		double r;
		double i;
		if (between(angle, 0, Math.PI / 2)) {
			r = 1;
			i = 1;
		} else if (between(angle, Math.PI / 2, Math.PI)) {
			r = -1;
			i = 1;
			angle = Math.PI - angle;
		} else if (between(angle, Math.PI, Math.PI * 3 / 2)) {
			r = -1;
			i = -1;
			angle = angle - Math.PI;
		} else {
			r = 1;
			i = -1;
			angle = Math.PI * 2 - angle;
		}

		double tanAngle = Math.tan(angle);
		double real = Math.sqrt(Math.pow(magnitude, 2) / (1 + Math.pow(tanAngle, 2)));
		double imaginary = real * tanAngle;

		return new ComplexNumber(r * real, i * imaginary);
	}

	/**
	 * Returns transformation of angle so it is in range between 0 and 2PI.
	 * 
	 * @param angle in radians.
	 * @return value of angle between 0 and 2PI e.
	 */
	public static double angleTransformBetween0and2Pi(double angle) {

		if (angle > 2 * Math.PI) {
			while (angle > Math.PI * 2) {
				angle -= Math.PI * 2;
			}
		} else if (angle < 0) {
			while (angle < 0) {
				angle += Math.PI * 2;
			}
		}
		return angle;
	}

	/**
	 * Method parses string and returns complex number represented by that string.
	 * valid inputs are(o-operator + - or nothing,R-real part,I imaginary part):
	 * oI(ex."-i" ,"+i") oR(ex."+2.21","2.21","-2.21") oRoI (ex."+2.21-2.21i"
	 * ,"2.21+2.21i") all other inputs result with thrown exception.
	 * 
	 * @param s string to be parsed into complex number.
	 * @return complex number represented by given string.
	 * @throws NumberFormatException if given string does not match rules given
	 *                               above.
	 * @throws NullPointerException  if given string is null.
	 */
	public static ComplexNumber parse(String s) {
		if (s == null)
			throw new NullPointerException();

		ObjectStack realStack = new ObjectStack();
		ObjectStack imaginaryStack = new ObjectStack();
		char[] stringArray = s.toCharArray();
		boolean imaginaryPresent = false;
		boolean imaginaryNumberTaken = false;

		// last index in array
		int index = stringArray.length - 1;
		if (stringArray[index] == 'i') {
			imaginaryPresent = true;
			// you do not need to check for last element in for loop
			index--;
		}

		for (; index >= 0; --index) {
			if (imaginaryPresent && !imaginaryNumberTaken) {
				imaginaryStack.push(stringArray[index]);
				// when you get to the first sign + or - it means the imaginary
				// part was taken.. so continue by taking real part
				if (stringArray[index] == '-' || stringArray[index] == '+') {
					imaginaryNumberTaken = true;
				}
			} else {
				realStack.push(stringArray[index]);
			}
		}

		double imaginary = 0;
		double real = 0;
		if (imaginaryPresent) {
			imaginary = parseStack(imaginaryStack, true);
		}

		if (!realStack.isEmpty()) {
			real = parseStack(realStack, false);
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method calculates and returns magnitude of @this complex number.
	 * 
	 * @return magnitude of @this complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Method calculates and returns angle of @this complex number Note that for
	 * number 0 function returns 0;
	 * 
	 * @return angle of @this complex number.
	 */
	public double getAngle() {

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
	 * Method adds given complex number with @this complex number.
	 * 
	 * @param c complex number to be added to @this complex number.
	 * @return sum of addition as new complex number.
	 * @throws NullPointerException if given complex number is null.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException();

		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Method subs given complex number from @this complex number.
	 * 
	 * @param c complex number to be subed from @this complex number.
	 * @return difference of @this complex number and given complex number.
	 * @throws NullPointerException if given complex number is null.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException();

		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Method multiples given complex number from @this complex number.
	 * 
	 * @param c complex number to be multiplied from @this complex number.
	 * @return product of @this complex number and given complex number.
	 * @throws NullPointerException if given complex number is null.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException();

		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.imaginary * c.real + this.real * c.imaginary);
	}

	/**
	 * Method divides @this complex number with given complex number.
	 * 
	 * @param c complex number to be divided from @this complex number.
	 * @return quotient of @this complex number divided from given complex number.
	 * @throws IllegalArgumentException if division is done by 0.
	 * @throws NullPointerException     if given complex number is null.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException();
		else if (c.getReal() == 0 && c.getImaginary() == 0)
			throw new IllegalArgumentException("Given complex number is 0.");

		ComplexNumber numerator = this.mul(new ComplexNumber(c.getReal(), -c.getImaginary()));
		ComplexNumber denominator = c.mul(new ComplexNumber(c.getReal(), -c.getImaginary()));

		return new ComplexNumber(numerator.getReal() / denominator.getReal(),
				numerator.getImaginary() / denominator.getReal());
	}

	/**
	 * Method calculates n power iv @this complex number.
	 * 
	 * @param n power on which calulation will be done.
	 * @return power n of @this complex number and returns it as new complex number.
	 * @throws IllegalArgumentException if n is less than 0.
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be grater or equal than 0.");

		if (n == 0) {
			return new ComplexNumber(1, 0);
		}

		double magintude = getMagnitude();
		double angle = getAngle();
		return fromMagnitudeAndAngle(Math.pow(magintude, n), angle * n);
	}

	/**
	 * Method calculates and returns nth root of @this complex number.
	 * 
	 * @param n nth of root on which calulation will be done.
	 * @return nth root n @this complex number and returns it as new complex number.
	 * @throws IllegalArgumentException if n is less than 1.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be grater than zero.");
		double magnitude = Math.pow(getMagnitude(), (double) 1 / n);
		double angle = getAngle();

		ComplexNumber[] roots = new ComplexNumber[n];

		for (int k = 0; k < n; ++k) {
			roots[k] = new ComplexNumber(magnitude * Math.cos((angle + 2 * Math.PI * k) / n),
					magnitude * Math.sin((angle + 2 * Math.PI * k) / n));
		}
		return roots;
	}

	/**
	 * 
	 * @param stack       holds char values to be parsed.
	 * @param isImaginary if true, options like i(empty stack) or +i and -i are
	 *                    considered.
	 * @return parsed number in double format.
	 * @throws NumberFormatException if char array on the stack is not able to
	 *                               parse.
	 */
	public static double parseStack(ObjectStack stack, boolean isImaginary) {
		String stringToParse = "";

		while (!stack.isEmpty()) {
			stringToParse = stringToParse + stack.pop();
		}

		if (isImaginary) {
			if (stringToParse.isEmpty() || stringToParse.contentEquals("+"))
				return 1;
			if (stringToParse.equals("-"))
				return -1;
		}

		// if not i or -i or +i just parse it
		return Double.parseDouble(stringToParse);
	}

	/**
	 * Method checks if value is between borders.
	 * 
	 * @param value      value to be check.
	 * @param lowBorder  is value greater than it.
	 * @param highBorder is value lower than it
	 * @return
	 */
	private static boolean between(double value, double lowBorder, double highBorder) {
		return (lowBorder < value && value < highBorder) ? true : false;
	}

	/**
	 * Getter for real part of complex number.
	 * 
	 * @return real component of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part of complex number.
	 * 
	 * @return imaginary component of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String imaginaryStr = "";
		String realStr = "";
		
		if(real==0 && imaginary==0) {
			realStr="0";
		}else {
			if(real!=0)
				realStr=Double.toString(real);
			
			if(imaginary!=0) {
					if(imaginary>0 && real!=0) imaginaryStr+=" + ";
					
					if(imaginary==1) imaginaryStr+= " i";
					else if(imaginary==-1) imaginaryStr+=" -i";
					else imaginaryStr+=Double.toString(imaginary) +"i";
			}
			
		}
		

		return "This complex number is: " + realStr + imaginaryStr;

	}
}
