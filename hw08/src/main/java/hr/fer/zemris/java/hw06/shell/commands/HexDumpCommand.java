package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Command which prints to the user hexdump of file provided with path.
 * 
 * @author Filip Hustić
 *
 */
public class HexDumpCommand implements ShellCommand {
	/**
	 * Name of the command.
	 */
	private final String commandName = "hexdump";
	/**
	 * Description of the command.
	 */
	private final String description = "Hexdump command prints out hexdump of file at given path.%n"
			+ "arg1-> path to the file";
	/**
	 * Constant which denotes how many hex dumps(bytes) will be in one section. One
	 * line consists of two sections.
	 */
	private final int BYTES_IN_SECTION = 8;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);

		Path filePath = null;
		try {
			filePath = Paths.get(lexer.nextToken().getValue()).normalize();

			if (!filePath.isAbsolute()) {
				filePath = env.getCurrentDirectory().resolve(filePath).normalize();
			}

			if (lexer.hasNext()) {
				System.out.println("To many arguments for command hexdump.");
				return ShellStatus.CONTINUE;
			}

		} catch (ShellLexerException ex) {
			System.out.println("Wrong format of arguments. " + ex.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		} catch (InvalidPathException ex) {
			env.writeln("Given path is not in right format or contains unsupported literals.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isRegularFile(filePath)) {
			System.out.println("Given path does not point to the file.");
			return ShellStatus.CONTINUE;
		}

		writeHexDump(env, filePath);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(Arrays.asList(description.split("%n")));
	}

	/**
	 * Helper method which writes hexdump of file at given path.
	 * 
	 * @param env   object which holds methods for communicating with user.
	 * @param input path to the file.
	 */
	private void writeHexDump(Environment env, Path input) {

		try (InputStream is = Files.newInputStream(input)) {

			int lineNumber = 0;
			while (true) {
				byte[] buff = new byte[BYTES_IN_SECTION * 2];

				for (int i = 0, bytesInLine = BYTES_IN_SECTION * 2; i < bytesInLine; ++i) {

					if (is.read(buff, i, 1) != 1) {
						if (i != 0)
							// write last one
							env.writeln(formatLine(buff, lineNumber));

						return;
					}
				}

				env.writeln(formatLine(buff, lineNumber));
				lineNumber += BYTES_IN_SECTION * 2;
			}

		} catch (IOException ex) {
			System.out.println("Problem while reading from file.");
		}

	}

	/**
	 * Helper method which formats line for hexdump output.
	 * 
	 * @param lineBytes  bytes of the line which will be presented.
	 * @param lineNumber number of line in hexdump.
	 * @return formated line.
	 */
	private String formatLine(byte[] lineBytes, int lineNumber) {
		StringBuilder line = new StringBuilder();
		byte dot = ".".getBytes()[0];

		String hexNumber = Integer.toHexString(lineNumber);
		line.append(String.format("0".repeat(8 - hexNumber.length()) + "%s: ", hexNumber));

		String hexRepresetation = Util.bytetohex(lineBytes).toUpperCase();

		for (int i = 0, bytesInLine = BYTES_IN_SECTION * 2; i < bytesInLine; ++i) {
			if (i == BYTES_IN_SECTION) {
				line.append("|");
			}
			String hex = hexRepresetation.substring(i * 2, i * 2 + 2);
			if (hex.equals("00")) {
				line.append("   ");
			} else {
				line.append(hex);
				line.append(" ");

				if (lineBytes[i] < 32)
					lineBytes[i] = dot;

			}
		}

		line.append("| ");
		line.append(new String(lineBytes));
		return line.toString();
	}

}
