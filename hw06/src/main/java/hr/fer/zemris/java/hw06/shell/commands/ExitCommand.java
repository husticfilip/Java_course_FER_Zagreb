package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents command which is called when shell stops with work.
 * 
 * @author Filip Hustić
 *
 */
public class ExitCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "exit";
	/**
	 * Description of the command.
	 */
	private final String description = "Command used to terminate shell.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
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
