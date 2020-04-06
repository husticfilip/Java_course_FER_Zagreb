package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents command which is used to set highest directory from
 * sharedData stack as current working directory of environment. SharedData
 * stack is placed in environment.
 * 
 * @author Filip Hustić
 *
 */
public class PopdCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "popd";
	/**
	 * Description of the command.
	 */
	private final String description = "Popd command is used to set highest directory from.%n"
			+ "sharedData stack as current working directory of environment%n"
			+ "command does not take in any additional arguments.";

	/**
	 * Key of stack in sharedDataMap in env.
	 */
	private final String CD_KEY = "cdstack";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);

		if (arguments == null || arguments.isEmpty()) {
			@SuppressWarnings("rawtypes")
			Stack st = (Stack) env.getSharedData(CD_KEY);

			if (st == null || st.isEmpty()) {
				env.writeln("There are no paths on stack.");
				return ShellStatus.CONTINUE;
			}

			env.setCurrentDirectory((Path) st.pop());
		} else {
			env.writeln("popd command expects no parameters.");
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
