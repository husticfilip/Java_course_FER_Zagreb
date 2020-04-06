package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents command which lists children files and directories of given
 * directory.
 * 
 * @author Filip Hustić
 *
 */
public class LsCommand implements ShellCommand {
	/**
	 * Name of the command.
	 */
	private static final String commandName = "ls";
	/**
	 * Description of the command.
	 */
	private final String description = "Ls command lists content of directory provided%n" + "through argument.%n"
			+ "arg1->directory path";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		Path pathTodir = null;
		try {
			pathTodir = getPathToDir(arguments);
		} catch (ShellLexerException ex) {
			env.writeln("Wrong argument format. " + ex.getLocalizedMessage());
			return ShellStatus.CONTINUE;
		} catch (InvalidPathException ex) {
			env.writeln("Given path is not in right format or contains unsupported literals.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(pathTodir)) {
			env.writeln("Directory does not exist.");
			return ShellStatus.CONTINUE;
		}

		try {

			List<Path> inDirectory = Files.list(pathTodir).collect(Collectors.toList());
			for (Path path : inDirectory) {
				writeOutDirectory(path, env);
			}

		} catch (IOException e) {
			env.writeln("Problem with walking through directroy.");
			return ShellStatus.CONTINUE;
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
	 * Helper method which returns path given through arguments.
	 * <p>
	 * If nothing is given through arguments path is considered currend directory
	 * and empty path is returned.
	 * <p>
	 * <p>
	 * If more than one argument is given exception is thrown.
	 * <p>
	 * 
	 * @param arguments holds path.
	 * @return path given through arguments.
	 * @throws ShellLexerException  if there was more than one argument.
	 * @throws InvalidPathException if given path is invalid.
	 * 
	 */
	public Path getPathToDir(String arguments) {
		// current directory
		if (arguments.isEmpty())
			return Paths.get("");

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);
		String dir = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			throw new ShellLexerException("There was more than 1 argument in ls command.");
		}

		return Paths.get(dir);
	}

	/**
	 * Method which gathers information of file or directory at given path.
	 * Information is propagated to the Environment object.
	 * 
	 * @param path to the file or directory.
	 * @param env  handles writing of gathered information.
	 * @throws IOException if there was mistake in writing.
	 */
	public void writeOutDirectory(Path path, Environment env) throws IOException {
		StringBuilder sb = new StringBuilder();

		if (Files.isDirectory(path))
			sb.append("d");
		else
			sb.append("-");

		if (Files.isReadable(path))
			sb.append("r");
		else
			sb.append("-");

		if (Files.isWritable(path))
			sb.append("w");
		else
			sb.append("-");

		if (Files.isExecutable(path))
			sb.append("e");
		else
			sb.append("-");

		sb.append(String.format("%10s", Files.size(path)));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		sb.append(String.format("  %s", formattedDateTime));
		sb.append(String.format("  %s", path.getFileName()));

		env.writeln(sb.toString());
	}

}
