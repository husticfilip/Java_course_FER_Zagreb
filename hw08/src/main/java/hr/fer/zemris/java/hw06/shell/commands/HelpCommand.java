package hr.fer.zemris.java.hw06.shell.commands;

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
 * Command which is called when description of some command is demanded.  
 * <p>Help command also lists all available commands.<p>
 * @author Filip Hustić
 *
 */
public class HelpCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private static final String commandName = "help";
	/**
	 * Description of the command.
	 */
	private final String description = "Help command is used for printing out available%n"
									 + "commands and their description.%n"
									 + "If given with no arguments help command prints out%n"
									 + "all commands."
									 + "If given with one argument help command prints out%n"
									 + "description of command given through argument%n."
									 + "arg1->name of the command";
									

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);

		if (arguments == null || arguments.isEmpty()) {
			env.commands().entrySet().forEach(e -> env.writeln(e.getKey()));
		} else {
			try {
				ShellLexer lexer = new ShellLexer(LexerModes.NAME_MODE, arguments);
				String name = lexer.nextToken().getValue();

				if (lexer.hasNext()) {
					env.writeln("To many arguments for command help.");
					return ShellStatus.CONTINUE;
				}
				
				if(env.commands().containsKey(name)) {
					List<String> description = env.commands().get(name).getCommandDescription();
					for(String line : description) 
						env.writeln(line);
					
				}else {
					System.out.println("Given command does not exist");
				}

			} catch (ShellLexerException ex) {
				env.writeln("Wrong format of arguments for command help. " + ex.getLocalizedMessage());
			}
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
