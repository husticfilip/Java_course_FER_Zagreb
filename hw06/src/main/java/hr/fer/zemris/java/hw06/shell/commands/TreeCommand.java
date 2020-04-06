package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Lexer.LexerModes;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexer;
import hr.fer.zemris.java.hw06.shell.Lexer.ShellLexerException;

/**
 * Class represents tree command.
 * <p>
 * Tree command prints out given directory and sub directories in form of tree.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class TreeCommand implements ShellCommand {
	/**
	 * Name of the command.
	 */
	private static final String commandName = "tree";
	/**
	 * Description of the command.
	 */
	private final String description = "Tree command is used to print out tree which starts%n"
									  + "at directory given through argument.%n"
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
		}catch (InvalidPathException ex) {
			env.writeln("Given path is not in right format or contains unsupported literals.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(pathTodir)) {
			env.writeln("Given path is not path to the directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(pathTodir, new ShellFileVisitor(env));

		} catch (ShellIOException ex) {
			env.writeln("Problem while writing to the console.Exiting.");
			return ShellStatus.TERMINATE;
		} catch (IOException ex) {
			env.writeln("Problem with walking file tree.");
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
	 * @throws ShellLexerException if there was more than one argument.
	 * @throws InvalidPathException if given path is invalid.
	 */
	public Path getPathToDir(String arguments) {
		// current directory
		if (arguments.isEmpty())
			return Paths.get("");

		ShellLexer lexer = new ShellLexer(LexerModes.PATH_MODE, arguments);
		String dir = lexer.nextToken().getValue();

		if (lexer.hasNext()) {
			throw new ShellLexerException("There was more than 1 argument in tree command.");
		}

		return Paths.get(dir);
	}

	/**
	 * Class implements file visitor which gets names of files and directories and
	 * calls environment method writeln with them as arguments.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private static class ShellFileVisitor implements FileVisitor<Path> {
		/**
		 * Level at which file or directory is.
		 */
		private int level;
		/**
		 * Object which handles writing out names of files and directories.
		 */
		private Environment env;

		public ShellFileVisitor(Environment env) {
			super();
			this.level = 0;
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(String.format(" ".repeat(level) + "%s", dir.getFileName().toString()));
			level += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(String.format(" ".repeat(level) + "%s", file.getFileName().toString()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.TERMINATE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level -= 2;
			return FileVisitResult.CONTINUE;
		}

	}
}
