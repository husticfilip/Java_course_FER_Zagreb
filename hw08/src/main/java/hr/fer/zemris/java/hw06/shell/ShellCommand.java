package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface which represents shell command.
 * 
 * @author Filip Hustić
 *
 */
public interface ShellCommand {

	/**
	 * Method executes command.
	 * 
	 * @param env       object which holds methods for communicating with user.
	 * @param arguments of the command.
	 * @return status Continue if command executed properly and Terminate if not.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * 
	 * @return name of command.
	 */
	String getCommandName();

	/**
	 * Description of command and how command is used.
	 * 
	 * @return description of command.
	 */
	List<String> getCommandDescription();

}
