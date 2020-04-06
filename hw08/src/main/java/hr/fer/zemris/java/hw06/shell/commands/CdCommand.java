package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents cd command which is used to step into given repository.
 * 
 * @author Filip Hustić
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "cd";
	/**
	 * Description of the command.
	 */
	private final String description = "Cd command is used to step into given repositry.%n"
			+ "Repository to be step to can be given in form of absolute path or of relative %n"
			+ "path which will be resolved with current directory." 
			+ "\narg1->directory to step into.%n";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);

		try {
			String path = lexer.nextToken().getValue();

			if (lexer.hasNext()) {
				env.writeln("Too many arguments for command cd. Expected one.");
				return ShellStatus.CONTINUE;
			}

			env.setCurrentDirectory(Paths.get(path));

		} catch (ShellLexerException ex) {
			env.writeln("Wrong type of arguments for command cd. " + ex.getLocalizedMessage());
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
