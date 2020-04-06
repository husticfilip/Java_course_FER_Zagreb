package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents command charset which lists names of supported charsetes.
 * 
 * @author Filip Hustić
 *
 */
public class CharsetsCommand implements ShellCommand {
	private final String commandName = "charset";
	private final String description = "Charset command is used for printing out all%n"
										+ "available charsets of used java platform.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);
		
		if (arguments != null && arguments.length() != 0) {
			env.writeln("Charset takes in no additional arguments.");
			return ShellStatus.CONTINUE;
		}

		Set<String> charsets = Charset.availableCharsets().keySet();
		StringBuilder charsetsBuilder = new StringBuilder();
		for (String charset : charsets) {
			charsetsBuilder.append(charset);
			charsetsBuilder.append("%n");
		}

		try {
			env.write(charsetsBuilder.toString());
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
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
