package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServletsUtil {

	private static String DIGEST_ALGORIHM = "SHA-256";

	public static String digestPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORIHM);

			return bytetohex(digest.digest(password.getBytes()));

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Provided digest algorithm does not exist", e);
		}
	}
	
	
	/**
	 * Method converts every byte of given array to hexadecimal value and appends it
	 * to string which represents hexadecimal representation of given byte array.
	 * 
	 * @param bytearray
	 * @return
	 */
	private static String bytetohex(byte[] bytearray) {

		StringBuilder sb = new StringBuilder();
		for (Byte byteNumber : bytearray) {
			sb.append(getHexValue((byte) ((byteNumber >> 4) & 0x0f)));
			sb.append(getHexValue((byte) (byteNumber & 0x0f)));
		}
		return sb.toString();
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
