package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * Class which holds main program to shell.
 * 
 * @author Filip Hustić
 *
 */
public class MyShell {

	/**
	 * Default symbol which denotes beginning of the command line in prompt.
	 */
	private static final char PROMPT_SYMBOL = '>';
	/**
	 * Default symbol which denotes more arguments in next line.
	 */
	private static final char MORE_LINES_SYMBOL = '\\';
	/**
	 * Default symbol which denotes beginning of next line of command.
	 */
	private static final char MULTIPLE_LINES_SYMBOL = '|';

	/**
	 * Entrance of shell program.
	 */
	public static void main(String[] args) {

		SortedMap<String, ShellCommand> commands = initializeCommands();

		try (Scanner sc = new Scanner(System.in);) {
			ShellEnviroment env = new ShellEnviroment(sc, commands, MULTIPLE_LINES_SYMBOL, PROMPT_SYMBOL,
					MORE_LINES_SYMBOL);

			env.writeln("Welcome to MyShell v 1.0");
			ShellStatus status;
			do {
				env.write(env.getPromptSymbol().toString() + " ");
				String line[] = env.readLine().trim().split(" ", 2);
				String command = line[0];

				if (command.isEmpty()) {
					status = ShellStatus.CONTINUE;
				} else {
					String arguments = line.length == 1 ? " " : line[1];

					if (arguments.charAt(arguments.length() - 1) == env.getMorelinesSymbol()) {
						// remove MORELINES symbol
						arguments = arguments.substring(0, arguments.length() - 1);
						arguments += " " + String.join(" ", readMoreLines(env));
					}

					if (env.commands().containsKey(command)) {

						status = env.commands().get(command).executeCommand(env, arguments.trim());

					} else {
						env.writeln("Command does not exist.");
						status = ShellStatus.CONTINUE;
					}
				}
			} while (status != ShellStatus.TERMINATE);

		} catch (ShellIOException ex) {
			System.out.println("Error while reading to enviorment or while writing to enviorment. Exiting");
			return;
		}

	}

	/**
	 * Method which reads lines while user enters MORE_LINES_SYMBOL.
	 * 
	 * @param env object which holds methods for communicating with user.
	 * @return list of String lines.
	 */
	private static List<String> readMoreLines(Environment env) {
		List<String> lines = new ArrayList<String>();

		String line;
		while (true) {
			env.write(env.getMultilineSymbol().toString() + " ");

			line = env.readLine();
			if (line.isEmpty()) {
				return lines;
			} else {
				line = line.trim();
			}

			if (line.charAt(line.length() - 1) == env.getMorelinesSymbol()) {
				lines.add(line.substring(0, line.length() - 1));
			} else if (!line.isEmpty()) {
				lines.add(line);
				return lines;
			}
		}
	}

	/**
	 * Method which initializes commands of shell and puts them in the map.
	 * 
	 * @return map of shell commands.
	 */
	private static SortedMap<String, ShellCommand> initializeCommands() {
		SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
		commands.put("cat", new CatCommand());
		commands.put("charset", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexDumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("cd", new CdCommand());
		commands.put("pwd",new PwdCommand());
		commands.put("pushd", new PushdCommand());
		commands.put("popd", new PopdCommand());
		commands.put("listd", new ListdCommand());
		commands.put("dropd", new DropdCommand());
		commands.put("massrename", new MassrenameCommand());
		return commands;
	}

}
