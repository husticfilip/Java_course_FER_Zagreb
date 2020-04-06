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
 * Class representing symbol command.
 * <p>
 * Symbols which value can be printed to user are PROMPT, MORELINES and
 * MULTILINE.
 * <p>
 * <p>
 * Also new symbols can be set to represent PROMPT, MORELINES and MULTILINE.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class SymbolCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private final String commandName = "symbol";
	/**
	 * Description of the command.
	 */
	private final String description = "Symbol command is used for printing out symbol%n"
									+ "of PROMPT, MORELINES or MULTILINE. Or for seting%n"
									+ "up new symbol for PROMPT, MORELINES or MULTILINE.%n"
									+ "arg1->PROMPT, MORELINES or MULTILINE%n"
									+ "arg2->new symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		ShellLexer lexer = new ShellLexer(LexerModes.NAME_MODE, arguments);

		String symbolName = "";
		String newSyimbol = "";
		try {
			symbolName = lexer.nextToken().getValue();

			if (lexer.hasNext())
				newSyimbol = lexer.nextToken().getValue();

			if (lexer.hasNext()) {
				env.writeln("To many arguments for command symbol.");
				return ShellStatus.CONTINUE;
			}
		} catch (ShellLexerException e) {
			env.writeln("Wrong format of arguments for command symbol. " + e.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		}

		if (newSyimbol.isEmpty())
			printSymbol(env, symbolName);
		else
			changeSybmol(env, symbolName, newSyimbol);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints desired symbol to the user.
	 * <p>
	 * Symbols which value can be printed to user are PROMPT, MORELINES and
	 * MULTILINE
	 * <p>
	 * 
	 * @param env        object which holds methods for communicating with user.
	 * @param symbolName name of the desired symbol which will be printed to user.
	 */
	private void printSymbol(Environment env, String symbolName) {

		if (symbolName.equals("PROMPT")) {
			env.writeln(String.format("Symbol for PROMPT is '%c'", env.getPromptSymbol()));
		} else if (symbolName.equals("MORELINES")) {
			env.writeln(String.format("Symbol for MORELINES is '%c'", env.getMorelinesSymbol()));
		} else if (symbolName.equals("MULTILINE")) {
			env.writeln(String.format("Symbol for MULTILINE is '%c'", env.getMultilineSymbol()));
		} else {
			env.writeln("Wrong name of symbol.");
		}

	}

	/**
	 * Method sets symbol to represent PROMPT, MORELINES or MULTILINE.
	 * 
	 * @param env        object which holds methods for communicating with user.
	 * @param symbolName PROMPT, MORELINES or MULTILINE.
	 * @param symbol     which will be assigned to given symbol name.
	 */
	private void changeSybmol(Environment env, String symbolName, String symbol) {
		if (symbol.length() != 1) {
			env.writeln("Symbol must be one char.");
			return;
		} else if (symbol.charAt(0) == '%') {
			env.writeln("% can not be used symbol.");
			return;
		}

		if (symbolName.equals("PROMPT")) {
			env.setPromptSymbol(symbol.charAt(0));
			env.writeln(String.format("New symbol for PROMPT is '%s'", symbol));
		} else if (symbolName.equals("MORELINES")) {
			env.setMorelinesSymbol(symbol.charAt(0));
			env.writeln(String.format("New symbol for MORELINES is '%s'", symbol));
		} else if (symbolName.equals("MULTILINE")) {
			env.setMultilineSymbol(symbol.charAt(0));
			env.writeln(String.format("New symbol for MULTILINE is '%s'", symbol));
		} else {
			env.writeln("Wrong name of symbol.");
		}

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
