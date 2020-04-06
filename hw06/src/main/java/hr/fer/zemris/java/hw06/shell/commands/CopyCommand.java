package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents copy command. Copy command takes in two arguments.
 * <p>
 * First is path to the file which will be copied and second is path to the file
 * to which first file will be copied or path to the directory to which first
 * file will be copied.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class CopyCommand implements ShellCommand {
	/**
	 * Name of the command.
	 */
	private final String commandName = "copy";
	/**
	 * Description of the command.
	 */
	private final String description = "Copy command is used to copy out content of input file%n"
										+ "to ouput file.%n"
										+ "arg1->path to input file arg2->path to output file%n"
										+ "If arg2 is directory copy of file has the same%n" 
										+ "name as original file.%n"
										+ "If output file does not exist new file with given name%n"
										+ "is made.";
										

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);

		Path inputPath = null;
		Path outputPath = null;
		try {
			inputPath = Paths.get(lexer.nextToken().getValue());
			outputPath = Paths.get(lexer.nextToken().getValue());

			if (lexer.hasNext()) {
				env.writeln("Too many arguments for command copy. Expected two.");
				return ShellStatus.CONTINUE;
			}

		} catch (ShellLexerException ex) {
			env.writeln("Wrong type of arguments for command copy. " + ex.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		} catch (InvalidPathException ex) {
			env.writeln("Given path is not in right format or contains unsupported literals.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isRegularFile(inputPath)) {
			env.writeln("Source path doesn not point to the file.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(outputPath)) {
			outputPath = Paths.get(outputPath.toString() + "\\" + inputPath.getFileName());

			if (!askToOverwriteIfNeeded(env, outputPath))
				return ShellStatus.CONTINUE;

		} else if (!Files.isRegularFile(outputPath) && outputPath.getParent() != null
				&& !Files.isDirectory(outputPath.getParent())) {
			env.writeln("Destination path doesn not exist.");
			return ShellStatus.CONTINUE;
		} else {

			if (!askToOverwriteIfNeeded(env, outputPath))
				return ShellStatus.CONTINUE;
		}

		writeFromFileToFile(env, inputPath, outputPath);
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
	 * Helper method called to check if given file already exist. If so user is
	 * through environment asked if overwriting is good with him.
	 * 
	 * @param env        object which communicates with user.
	 * @param outputPath path to file which is being check.
	 * @return true if file does not already exist or if it exist and user gives
	 *         confirmation to overwrite. If file exist and user does not give
	 *         confirmation to overwrite false is returned.
	 */
	private boolean askToOverwriteIfNeeded(Environment env, Path outputPath) {

		if (Files.isRegularFile(outputPath)) {
			env.writeln("File at given destination already exist. Do you want to overwrite it?");

			if (!env.readLine().toLowerCase().equals("yes"))
				return false;
			else
				return true;
		}
		return true;
	}

	/**
	 * Method which writes from file at inputPath to file to outputPath.
	 * 
	 * @param env        object which communicates with user.
	 * @param inputPath  path of file which will be copied.
	 * @param outputPath path to the file to which first file will be copied.
	 */
	private void writeFromFileToFile(Environment env, Path inputPath, Path outputPath) {

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(inputPath))));
				Writer bw = new BufferedWriter(
						new OutputStreamWriter(new BufferedOutputStream(Files.newOutputStream(outputPath))))) {

			String newLine = System.getProperty("line.separator");
			String line;
			while ((line = br.readLine()) != null) {
				bw.write(line + newLine);
			}
		} catch (IOException ex) {
			env.writeln("There was mistake in reading and writing.");
		}

	}

}
