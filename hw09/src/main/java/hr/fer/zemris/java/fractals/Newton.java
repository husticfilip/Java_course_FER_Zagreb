package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class which holds main method which activates FractalViewer.
 * 
 * @author Filip Hustić
 *
 */
public class Newton {

	/**
	 * Entrance point of program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n");
			System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

			ArrayList<Complex> roots = new ArrayList<Complex>();
			int rootNumber = 1;
			while (true) {
				System.out.printf("Root %d>", rootNumber);
				String line = sc.nextLine();
				if (line.equals("done")) {
					break;
				}
				Complex root = parseToComplex(line);
				if (root == null) {
					System.out.println("Wrong number format, skipping.");
				} else {
					roots.add(root);
					rootNumber++;
				}
			}

			Complex[] rootsArray = new Complex[roots.size()];
			for (int i = 0; i < rootsArray.length; ++i) {
				rootsArray[i] = roots.get(i);
			}

			NewtonFractalProducer producer = new NewtonFractalProducer(
					new ComplexRootedPolynomial(Complex.ONE, rootsArray));
			FractalViewer.show(producer);
		}

	}

	/**
	 * Method parses given input to complex number. If parse can not be done null is
	 * returned.
	 * 
	 * @param input which represents complex number.
	 * @return complex number presented by input, if parse of given input can not be
	 *         done null is returned.
	 */
	public static Complex parseToComplex(String input) {
		Pattern realPattern = Pattern.compile("^[+-]{0,1}[ ]*[0-9]+[\\.]{0,1}[0-9]*$");
		Pattern imaginaryPattern = Pattern.compile("^[+-]{0,1}[ ]*i[0-9]+[\\.]{0,1}[0-9]*$");
		Pattern realAndImaginiaryPositive = Pattern
				.compile("^[+-]{0,1}[ ]*[0-9]+[\\.]{0,1}[0-9]*[ ]*\\+[ ]*i[0-9]+[\\.]{0,1}[0-9]*$");
		Pattern realAndImaginiaryNegative = Pattern
				.compile("^[+-]{0,1}[ ]*[0-9]+[\\.]{0,1}[0-9]*[ ]*\\-[ ]*i[0-9]+[\\.]{0,1}[0-9]*$");
		Pattern onlyI = Pattern.compile("^[+]{0,1}[ ]*i$");
		Pattern OnlyINeg = Pattern.compile("^[-][ ]*i$");

		input = input.trim().replaceAll("-[ ]*", "-").replace("+[ ]*", "+");
		try {
			if (onlyI.matcher(input).find()) {
				return new Complex(0, 1);
			} else if (OnlyINeg.matcher(input).find()) {
				return new Complex(0, -1);
			} else if (realPattern.matcher(input).find()) {
				return new Complex(Double.parseDouble(input), 0);
			} else if (imaginaryPattern.matcher(input).find()) {
				return new Complex(0, Double.parseDouble(input.replace("i", "")));
			} else if (realAndImaginiaryPositive.matcher(input).find()) {
				String real = input.substring(0, input.lastIndexOf('+'));
				String imaginary = input.substring(input.lastIndexOf("+") + 1).replace('i', '+');
				return new Complex(Double.parseDouble(real), Double.parseDouble(imaginary));
			} else if (realAndImaginiaryNegative.matcher(input).find()) {
				String real = input.substring(0, input.lastIndexOf('-'));
				String imaginary = input.substring(input.lastIndexOf("-") + 1).replace('i', '-');
				return new Complex(Double.parseDouble(real), Double.parseDouble(imaginary));
			}

		} catch (NumberFormatException e) {
		}

		return null;
	}

}
