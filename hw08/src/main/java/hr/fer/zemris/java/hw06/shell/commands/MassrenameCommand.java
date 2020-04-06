package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;
import hr.fer.zemris.java.hw06.shell.nameFilter.FilterResult;
import hr.fer.zemris.java.hw06.shell.nameFilter.NameBuilder;
import hr.fer.zemris.java.hw06.shell.nameFilter.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.nameFilter.NameParserException;

/**
 * Class represents command for renaming and moving multiple files at once from
 * one directory to other directory.
 * <p>
 * Files which will be moved are determined by regex given as argument of
 * command. All files which contains given regex are moved and renamed to name
 * given as second argument.
 * <p>
 * <p>
 * New name can contains parts of first name which are grouped in regex. Those
 * parts are placed in new name by expression ${number_of_group} or
 * ${number_of_group,min_length}.
 * <p>
 * <p>
 * Padding is added if length of group is less than min_length. If first digit
 * of padding is 0, padding is added with zeros. ex ${2,02} and if group 2 is
 * "a" , new value is "00a". If first digit is not 0 then padding is done with
 * spaces.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class MassrenameCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private static final String commandName = "massrename";
	/**
	 * Description of the command.
	 */
	private final String description = "Massranem command renames and moves multiple files at once from one%n" +
									   "directory to other.%n"+
									   "Files which will be moved are determined by regex given as argument%n"+ 
									   "of command. All files which contains given regex are moved and renamed to%n"+
									   "name given as second argument.%n"+
									   "New name can contain parts of first name which are grouped in regex. Those%n"+
									   "parts are placed in new name by expression ${number_of_group} or ${number_of_group,min_length}.%n"+
									   "Padding is added if length of group is less than min_length. If first digit%n"+
									   "of padding is 0, padding is added with zeros. ex ${2,02} and if group 2 is \"a\",%n"+ 
									   "new value is \"00a\". If first digit is not 0 then padding is done with spaces.%n"+
									   "%n"+
									   "Supported subcomands are:%n"+
									   "filter->prints out all files which name mathces given regex.%n"+
									   "groups->prints out all groups provied in regex for every file which matches given regex.%n"+
									   "show->shows new name after substitution is done.%n"+
									   "execute->renames and moves files from one directroy to other.%n"+
									   "%n"+
									   "arg1->subcommand    arg2->source directroy    arg3->destination directroy%n"+
									   "arg4->regular expression supported by java.util.regex.Pattern%n"+
									   "arg5->substitution expression for new file name.";
	
	
	/**
	 * Flter sub command.
	 */
	private final String FILTER_COMMAND = "filter";
	/**
	 * Groups sub command.
	 */
	private final String GROUPS_COMMAND = "groups";
	/**
	 * show sub command.
	 */
	private final String SHOW_COMMAND = "show";
	/**
	 * execute sub command.
	 */
	private final String EXECUTE_COMMAND = "execute";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		try {
			ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);
			Path sourceDir = getPath(env, lexer.nextToken().getValue()).normalize();
			Path destDir = getPath(env, lexer.nextToken().getValue()).normalize();

			if (!Files.isDirectory(sourceDir)) {
				env.writeln("Source directory does not exist.");
				return ShellStatus.CONTINUE;
			}

			lexer.setMode(LexerModes.NAME_MODE);
			String subCommand = lexer.nextToken().getValue();

			if (subCommand.equals(FILTER_COMMAND)) {
				filterCommand(env, lexer, sourceDir);
			} else if (subCommand.equals(GROUPS_COMMAND)) {
				groupsCommand(env, lexer, sourceDir);
			} else if (subCommand.equals(SHOW_COMMAND)) {
				showCommand(env, lexer, sourceDir);
			} else if (subCommand.equals(EXECUTE_COMMAND)) {
				commandExecute(env, lexer, sourceDir, destDir);
			} else {
				env.writeln("Wrong subcomand.");
			}

		} catch (ShellLexerException ex) {
			env.writeln("Wrong type of arguments for command massrename. " + ex.getLocalizedMessage());
		} catch (InvalidPathException ex) {
			env.writeln("Wrong path.");
		} catch (PatternSyntaxException ex) {
			env.writeln("Wrong syntax of pattern");
		} catch (IOException ex) {
			env.writeln("Error while reading or writing files.");
		} catch (NameParserException ex) {
			env.writeln("Substituton pattern is not valid." + ex.getLocalizedMessage());
		} catch (IndexOutOfBoundsException ex) {
			env.writeln(ex.getLocalizedMessage());
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

	/**
	 * Sub command of massrename which prints to environment all files with name
	 * matching giving pattern.
	 * 
	 * @param env       environment.
	 * @param lexer     lexer.
	 * @param sourceDir directory of files.
	 * @throws PatternSyntaxException if given pattern is invalid.
	 * @throws IOException
	 */
	private void filterCommand(Environment env, ShellLexer lexer, Path sourceDir)
			throws PatternSyntaxException, IOException {
		lexer.setMode(LexerModes.PATTERN_MODE);
		String pattern = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			env.writeln("Too many arguments for subcommand filter.");
			return;
		} else {
			FilterResult.filter(sourceDir, pattern).forEach(f -> env.writeln(f.toString()));
		}
	}

	/**
	 * Command which prints to environment groups for every file name which matched
	 * given pattern.
	 * 
	 * @param env       working environment.
	 * @param lexe      lexer.
	 * @param sourceDir source directory of files to be matched.
	 * @throws PatternSyntaxException if given pattern is not valid.
	 * @throws IOException            if there was exception while reading or
	 *                                writing file.
	 */
	private void groupsCommand(Environment env, ShellLexer lexer, Path sourceDir)
			throws PatternSyntaxException, IOException {
		lexer.setMode(LexerModes.PATTERN_MODE);
		String pattern = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			env.writeln("Too many arguments for subcommand groups.");
			return;
		} else {
			FilterResult.filter(sourceDir, pattern).forEach((filRes) -> {
				env.write(filRes.toString() + "   ");

				for (int i = 0, numOfGroups = filRes.numberOfGroups(); i <= numOfGroups; ++i) {
					env.write(String.format("%d: %s  ", i, filRes.group(i)));
				}

				env.write("%n");
			});

		}
	}

	/**
	 * Command which shows new file names when substitution of name is done.
	 * Substitution is done on files which are matching with given regex.
	 * 
	 * @param env       working environment.
	 * @param lexer     lexer.
	 * @param sourceDir sourceDir source directory of files to be matched.
	 * @throws PatternSyntaxException if given pattern is not valid.
	 * @throws IOException            if there was exception while reading or
	 *                                writing file.
	 */
	private void showCommand(Environment env, ShellLexer lexer, Path sourceDir)
			throws PatternSyntaxException, IOException {
		lexer.setMode(LexerModes.PATTERN_MODE);

		String pattern = lexer.nextToken().getValue();
		String expression = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			env.writeln("Too many arguments for subcommand show.");
			return;
		} else {
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder nameBuilder = parser.getNameBuilder();

			FilterResult.filter(sourceDir, pattern).forEach((filterResult) -> {
				StringBuilder sb = new StringBuilder();
				nameBuilder.execute(filterResult, sb);
				env.writeln(String.format("%s => %s", filterResult.toString(), sb.toString()));
			});

		}
	}

	/**
	 * Command which finds all files whose name matches given regex, renames them
	 * with given substitution pattern and moves them to destination directory.
	 * 
	 * @param env       working environment.
	 * @param lexer     lexer.
	 * @param sourceDir sourceDir source directory of files to be matched.
	 * @param destDir   destination directory.
	 * @throws PatternSyntaxException if given pattern is not valid.
	 * @throws IOException            if there was exception while reading or
	 *                                writing file.
	 */
	private void commandExecute(Environment env, ShellLexer lexer, Path sourceDir, Path destDir)
			throws PatternSyntaxException, IOException {
		if (!Files.isDirectory(destDir)) {
			env.writeln("Destination directory does not exist.");
			return;
		}

		lexer.setMode(LexerModes.PATTERN_MODE);
		String pattern = lexer.nextToken().getValue();
		String expression = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			env.writeln("Too many arguments for subcommand execute.");
			return;

		} else {
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder nameBuilder = parser.getNameBuilder();

			List<FilterResult> results = FilterResult.filter(sourceDir, pattern);

			for (FilterResult filterResult : results) {
				Path sourcePath = sourceDir.resolve(filterResult.toString());

				StringBuilder sb = new StringBuilder();
				nameBuilder.execute(filterResult, sb);
				Path destPath = destDir.resolve(sb.toString().trim());

				Files.move(sourcePath, destPath);
				env.writeln(String.format("%s => %s", sourcePath, destPath));
			}
		}

	}

	/**
	 * Helper method which returns absolute path of given path.
	 * 
	 * @param env        working environment.
	 * @param stringPath path.
	 * @return aboslut path.
	 */
	private Path getPath(Environment env, String stringPath) {
		Path path = Paths.get(stringPath).normalize();

		if (!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path);
		}

		return path;
	}

}
