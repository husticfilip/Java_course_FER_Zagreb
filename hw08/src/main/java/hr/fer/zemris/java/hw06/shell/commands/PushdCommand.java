package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents command which is used to push current directory on top of
 * sharedData stack placed in environment. After the push new working directory
 * becomes one given as argument of command.
 * 
 * @author Filip Hustić
 *
 */
public class PushdCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "pushd";
	/**
	 * Description of the command.
	 */
	private final String description = "Pushd command is used to push current directory on top of.%n"
			+ "sharedData stack placed in environment.%n"
			+ "After the push new working directory becomes one given as %n" + "argument of command.%n"
			+ "arg1->path to new working directory.";

	/**
	 * Key of stack in sharedDataMap in env.
	 */
	private final String CD_KEY = "cdstack";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);

		try {
			ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);
			Path path = Paths.get(lexer.nextToken().getValue());

			if (lexer.hasNext()) {
				env.writeln("Too many arguments for command pushd. Expected one.");
				return ShellStatus.CONTINUE;
			}

			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(CD_KEY);
			if (stack == null) {
				stack = new Stack<Path>();
			}

			Path oldDirectory = env.getCurrentDirectory();

			env.setSharedData(CD_KEY, stack);
			env.setCurrentDirectory(path);

			stack.push(oldDirectory);

		} catch (ShellLexerException ex) {
			env.writeln("Wrong type of arguments for command pushd. " + ex.getLocalizedMessage());
		} catch (InvalidPathException | ShellIOException ex) {
			env.writeln(
					"Provided path is not valid. It consists of illegal literals or direcotry at given path does not exist.");
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
