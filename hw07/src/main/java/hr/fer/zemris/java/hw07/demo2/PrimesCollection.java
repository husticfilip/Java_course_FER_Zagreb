package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Class which represents generator of prime numbers.
 * <p>
 * Class implements Iterable has factory method which returns iterator of prime
 * numbers.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of prime numbers allowed for iterator to calculate.
	 */
	private int primesNumber;

	/**
	 * Default constructor which takes in number of prime numbers allowed for
	 * iterator to calculate.
	 * 
	 * @param primesNumber
	 */
	public PrimesCollection(int primesNumber) {
		super();
		this.primesNumber = primesNumber;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator(primesNumber);
	}

	/**
	 * Iterator which in every iteration generates next prime number.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class PrimeIterator implements Iterator<Integer> {
		/**
		 * Number of primes numbers which can be generated in one instance of this
		 * class.
		 */
		int primesNumber;
		/**
		 * Last generated prime number.
		 */
		int currentNumber;
		/**
		 * Number of generated prime numbers;
		 */
		int returnedNumber;

		/**
		 * Default constructor which takes in number of prime numbers allowed for
		 * iterator to calculate.
		 * 
		 * @param primesNumber
		 */
		public PrimeIterator(int primesNumber) {
			this.primesNumber = primesNumber;
			returnedNumber = 0;
			currentNumber = 2;
		}

		@Override
		public boolean hasNext() {
			return returnedNumber < primesNumber;
		}

		@Override
		public Integer next() {
			if (!hasNext())
				throw new IndexOutOfBoundsException("Generated all prime numbers.");

			while (true) {
				boolean flag = false;
				for (int i = 2, sqrt = (int) Math.sqrt(currentNumber); i <= sqrt; ++i) {
					if (currentNumber % i == 0) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					returnedNumber++;
					return currentNumber++;
				}
				currentNumber++;
			}

		}

	}

}
