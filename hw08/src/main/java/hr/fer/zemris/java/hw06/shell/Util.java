package hr.fer.zemris.java.hw06.shell;

import java.util.Objects;

/**
 * Class holds methods for converting byte array to hexadecimal value and vice
 * versa.
 * 
 * @author Filip Hustić
 *
 */
public class Util {

	/**
	 * Method converts given string which represents hexadecimal values to byte
	 * array.
	 * 
	 * @param keyText which will be converted to byte array.
	 * @return byte array which represents given string of hexadecimal values.
	 * @throws IllegalArgumentException if some char of keyText is not hexadecimal
	 *                                  representative or if number of chars in
	 *                                  keyText is not even.
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText);
		if (keyText.length() % 2 != 0)
			throw new IllegalArgumentException("Hex value was not even.");

		if (keyText.length() == 0) {
			return new byte[0];

		} else {
			byte[] byteArray = new byte[keyText.length() / 2];

			for (int i = 0; i < byteArray.length; ++i) {
				byte lowerFour = getNumberValue(keyText.charAt((i * 2 + 1)));
				byte higherFour = (byte) (getNumberValue(keyText.charAt(i * 2)) << 4);
				byteArray[i] = (byte) (lowerFour + higherFour);
			}

			return byteArray;
		}
	};

	/**
	 * Method converts every byte of given array to hexadecimal value and appends it
	 * to string which represents hexadecimal representation of given byte array.
	 * 
	 * @param bytearray
	 * @return
	 */
	public static String bytetohex(byte[] bytearray) {

		StringBuilder sb = new StringBuilder();
		for (Byte byteNumber : bytearray) {
			sb.append(getHexValue((byte) ((byteNumber >> 4) & 0x0f)));
			sb.append(getHexValue((byte) (byteNumber & 0x0f)));
		}
		return sb.toString();
	}

	/**
	 * Helper method which returns byte value of given symbol.
	 * 
	 * @param symbol representing hexadecimal value.
	 * @return byte value of given symbol.
	 * @throws IllegalArgumentException of given symbol is not hexadecimal
	 *                                  representative.
	 */
	private static byte getNumberValue(char symbol) {

		if (Character.isDigit(symbol)) {
			return (byte) Character.getNumericValue(symbol);
		} else {
			if (symbol == 'a' || symbol == 'A')
				return 10;
			else if (symbol == 'b' || symbol == 'B')
				return 11;
			else if (symbol == 'c' || symbol == 'C')
				return 12;
			else if (symbol == 'd' || symbol == 'D')
				return 13;
			else if (symbol == 'e' || symbol == 'E')
				return 14;
			else if (symbol == 'f' || symbol == 'F')
				return 15;
			else
				throw new IllegalArgumentException("Symbol is not representative of hexadecimal value.");
		}
	}

	/**
	 * Method returns hexadecimal representative of given byte.
	 * 
	 * @param byteNumber number which can be represented as single symbol
	 *                   hexadecimal value-
	 * @return hexadecimal representative of given byte.
	 * @throws IllegalArgumentException if given number is greater than 15 or less
	 *                                  than 0.
	 */
	private static String getHexValue(Byte byteNumber) {

		if (byteNumber < 10) {
			return byteNumber.toString();
		} else {
			if (byteNumber == 10)
				return "a";
			else if (byteNumber == 11)
				return "b";
			else if (byteNumber == 12)
				return "c";
			else if (byteNumber == 13)
				return "d";
			else if (byteNumber == 14)
				return "e";
			else if (byteNumber == 15)
				return "f";
			else
				throw new IllegalArgumentException("Number can not be representet with 4 bits.");
		}

	}

}
