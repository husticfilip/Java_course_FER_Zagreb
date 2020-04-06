package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents command which prints current working directory on
 * environment.
 * 
 * @author Filip Hustić
 *
 */
public class PwdCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "pwd";
	/**
	 * Description of the command.
	 */
	private final String description = "Pwd command is used to print current working directory. .%n"
			+ "command does not take in any additional arguments.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);

		if (arguments == null || arguments.isEmpty()) {
			env.writeln(env.getCurrentDirectory().toString());
		} else {
			env.writeln("pwd command expects no parameters.");
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
