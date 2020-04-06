package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
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
 * Command with which user can make new directories. If directori already exists
 * nothing new is made.
 * <p>
 * If parent path of given directory does not exist message is output to user
 * and no new directories are made.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class MkdirCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "mkdir";
	/**
	 * Description of the command.
	 */
	private final String description = "Mkdir command creates directory with path given%n"
									+ "through argument.%n"
									+ "arg1->directory path";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);

		Path dir = null;
		try {
			dir = Paths.get(lexer.nextToken().getValue()).toAbsolutePath();

			if (lexer.hasNext()) {
				env.writeln("Command mkdir takes in just one argument.");
				return ShellStatus.CONTINUE;
			}

		} catch (ShellLexerException e) {
			env.writeln("Wrong format of arguments for command dir. " + e.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		} catch (InvalidPathException ex) {
			env.writeln("Given path is not in right format or contains unsupported literals.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(dir.getParent())) {
			env.writeln("Parents of given child direcory do not exist.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectories(dir);
		} catch (IOException e) {
			env.writeln("Error while creating directory.");
			return ShellStatus.CONTINUE;
		}
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

}
