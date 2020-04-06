package hr.fer.zemris.java.custom.collections.demo;

import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class which holds method for calculating expression in postifix notation.
 * 
 * @author Filip Hustić
 *
 */
public class StackDemo {

	/**
	 * Enterance point of program, main method.
	 * 
	 * @param args args[0] holds string in postfix notation which then be calulated.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("You did not enter valid number of arguments. Valid number is one. Expression must be single string "
					+ "enclosed into question marks. ex \"3 4 +\"");
			return;
		}

		ObjectStack stack = new ObjectStack();
		calculation(args[0], stack);

	}

	/**
	 * Method calculates expression which is in POSTFIX notation. If there are is
	 * not enough numbers or there is to many numbers in expression, method prints
	 * message about it to user and does not continue calculation. If expression
	 * contains invalid operator, message about it is printed to user and
	 * calculation does not continue. If there is operation of division with 0,
	 * message is printed to user and calculation does not continue. In all other
	 * cases expresion is calculated and printed to user.
	 * 
	 * @param expression to be calculated.
	 * @param stack      used to suport the calculation.
	 */
	public static void calculation(String expression, ObjectStack stack) {

		Scanner line = new Scanner(expression.trim());
		while (line.hasNext()) {
			if (line.hasNextInt()) {
				stack.push(Integer.parseInt(line.next()));
			} else {
				try {
					int value2 = (int) stack.pop();
					int value1 = (int) stack.pop();
					String operator = line.next();
					if ((operator.equals("/") || operator.equals("%")) && value2 == 0) {
						System.out.println("Division with zero is not possible");
						line.close();
						return;
					}
					switch (operator) {
					case "+":
						stack.push(value1 + value2);
						break;
					case "-":
						stack.push(value1 - value2);
						break;
					case "*":
						stack.push(value1 * value2);
						break;
					case "/":
						stack.push(value1 / value2);
						break;
					case "%":
						stack.push(value1 % value2);
						break;
					default:
						System.out.printf("Invalid expression, %s is not an opeartor", operator);
						return;
					}
					//if stack is empty there was not enough numbers in expression.
				} catch (EmptyStackException ex) {
					System.out.println("There was not enough numbers in expression, calculation is not possible");
					line.close();
					return;
				}
			}
		}
		//close scanner
		line.close();
		if (stack.size() != 1) {
			System.out.println("There was to many numeric arguments in expresion, calculation is not possible.");
		} else {
			System.out.printf("Result of calculation is: %d", stack.pop());
		}
	}
}
