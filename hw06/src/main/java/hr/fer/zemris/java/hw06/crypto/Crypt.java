package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class holds main program which is used for calculating digest of input file
 * or for encrypting and decrypting of input file.
 * <p>
 * Digest is calculating using SHA-256 algorithm.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class Crypt {

	/**
	 * Enter point of program. Program calculates digest of file with given path.
	 * 
	 * @param args args[0] holds path to file.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {

			if (args[0].equals("checksha")) {
				degistCheckSha(args[1]);
			} else {
				System.out.println("Wrong argument, checksha expected.");
			}

		} else if (args.length == 3) {

			if (args[0].equals("encrypt")) {
				encryptDecrytp(args[1], args[2], true);
			} else if (args[0].equals("decrypt")) {
				encryptDecrytp(args[1], args[2], false);
			} else {
				System.out.println("Wrong argument, encrypt or dencrypt expected.");
			}

		} else {
			System.out.println("Wrong number of arguments");
		}

	}

	/**
	 * Method which takes in path to file which will be encrypted or decrypted and
	 * path to which new file will be written.
	 * 
	 * @param stringReadPath  path to input file.
	 * @param stringWritePath path to output file.
	 * @param encrypt         boolean which indicates if encryption or decryption
	 *                        will be done. If set to true encryption is done, if
	 *                        set to false decryption is done.
	 */
	private static void encryptDecrytp(String stringReadPath, String stringWritePath, Boolean encrypt) {

		Path readPath = Paths.get(stringReadPath);
		Path writePath = Paths.get(stringWritePath);

		try (Scanner sc = new Scanner(System.in);
				InputStream is = Files.newInputStream(readPath);
				OutputStream os = Files.newOutputStream(writePath)) {

			System.out.printf("Please provide password as hex-encoded text (16 bytes,i.e. 32 hex-digits):%n>");
			String keyText = sc.next();

			System.out.printf("Please provide initialization vector as hex-encoded text(32 hex-digits):%n>");
			String ivText = sc.next();

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			crypt(is, os, cipher);
			System.out.printf("Decryption completed. Generated file %s based on file %s.", writePath, readPath);

		} catch (Exception ex) {
			System.out.println("Error while encrypting/decrptying or file does not exist.");
		}

	}

	/**
	 * Method crypts given inputStream and writes crypted result into outputStream.
	 * 
	 * @param is     input stream.
	 * @param os     output stream.
	 * @param cipher which holds key by which cryptation will be done.
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void crypt(InputStream is, OutputStream os, Cipher cipher)
			throws IOException, IllegalBlockSizeException, BadPaddingException {

		while (true) {
			byte[] buffer = new byte[1024];
			int readLength = is.read(buffer);
			if (readLength < 1)
				break;

			os.write(cipher.update(buffer, 0, readLength));
		}
		os.write(cipher.doFinal());

	}

	/**
	 * Method which takes in path to file of which digest will be calculated. Digest
	 * is done using sha-256 algorithm.
	 * 
	 * @param stringPath to the file from which digest will be calculated.
	 */
	private static void degistCheckSha(String stringPath) {
		Path path = Paths.get(stringPath);
		try (Scanner sc = new Scanner(System.in); InputStream is = Files.newInputStream(path);) {
			String digestString = calculateDigest(is);

			System.out.printf("Please provide expected sha-256 digest for hw06test.bin:%n>");
			String input = sc.nextLine();

			if (digestString.equals(input)) {
				System.out.printf("Digesting completed. Digest of %s matches expected digest.", path.getFileName());
			} else {
				System.out.printf(
						"Digesting completed. Digest of %s does not match the expected digest. Digest was: %s",
						path.getFileName(), digestString);
			}

		} catch (Exception e) {
			System.out.println("There was error while reading from file or file does not exist.");
		}

	}

	/**
	 * Method calculates digest of file.
	 * 
	 * @param is input stream of file.
	 * @return String representing hexadecimal value of digest.
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private static String calculateDigest(InputStream is) throws IOException, NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		byte[] buffer = new byte[4 * 1024];
		while (true) {
			int readLength = is.read(buffer);
			if (readLength < 1)
				break;
			digest.update(buffer, 0, readLength);
		}

		return Util.bytetohex(digest.digest());
	}

}
