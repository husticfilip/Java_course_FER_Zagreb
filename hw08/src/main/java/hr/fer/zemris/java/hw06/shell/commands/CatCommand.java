package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
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
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents cat command which reads file at given path on consle.
 * 
 * @author Filip Hustić
 *
 */
public class CatCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "cat";
	/**
	 * Description of the command.
	 */
	private final String description = "Cat command is used for printing out content of file%n"
			+ "with given path provided as first argument. And %n"
			+ "writing it out to the console with charset provided %n" + "as second argument.%n"
			+ "arg1->path to input file arg2->desired charset%n";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);

		String path = "";
		String providedCharset = null;
		try {
			path = lexer.nextToken().getValue();

			if (lexer.hasNext()) {
				lexer.setMode(LexerModes.NAME_MODE);
				providedCharset = lexer.nextToken().getValue();

				if (lexer.hasNext()) {
					env.writeln("Too many arguments for command cat. One or two is expected.");
					return ShellStatus.CONTINUE;
				}

			}
		} catch (ShellLexerException ex) {
			env.writeln("Wrong type of arguments for command cat. " + ex.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		}

		readFromFile(env, path, providedCharset);

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
	 * Helper method which reads from file at given path with provided charset and
	 * calls enviorments method write with read data as argument.
	 * <p>
	 * If charset is not provided default charset is set for reading. If provided
	 * charset is not supported exception is being thrown and reading is not done.
	 * <p>
	 * <p>
	 * If file exists at given path reading is done, otherwise exception is thrown.
	 * <p>
	 * 
	 * @param environment     object which holds method write.
	 * @param path            to the file.
	 * @param providedCharset charset with which file will be read.
	 * @throws ShellIOException if given path is not file path, if given charset is
	 *                          not supported, if there was mistake with writing to
	 *                          environment or if file was not able to read.
	 */
	private void readFromFile(Environment env, String path, String providedCharset) throws ShellIOException {

		Charset charset;
		try {
			if (providedCharset == null) {
				charset = Charset.defaultCharset();
			} else if (!Charset.isSupported(providedCharset)) {
				env.writeln("Provided charset is not supported.");
				return;
			} else {
				charset = Charset.forName(providedCharset);
			}
		} catch (IllegalCharsetNameException ex) {
			env.writeln("Provided charset is not supported.");
			return;
		}

		try {
			Path pathToFile = Paths.get(path);
			
			if(!pathToFile.isAbsolute()) {
				pathToFile = env.getCurrentDirectory().resolve(path);
			}
			
			if (!Files.isRegularFile(pathToFile)) {
				env.writeln("File does not exist.");
				return;
			}

			BufferedReader br = Files.newBufferedReader(pathToFile, charset);

			String line = "";
			while ((line = br.readLine()) != null) {
				env.writeln(line);
			}

		} catch (IOException|InvalidPathException ex) {
			env.writeln("Exception while reading file.");
			return;
		}

	}

}
