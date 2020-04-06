package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * Class which represents Environment of shell. Through this class communication
 * between shell and user is done.
 * 
 * @author Filip Hustić
 *
 */
public class ShellEnviroment implements Environment {
	/**
	 * Scanner which reads from input.
	 */
	private Scanner sc;
	/**
	 * Map of available commands.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Symbol which denotes beginning of next line of command.
	 */
	private char multipleSymbol;
	/**
	 * Symbol which denotes beginning of the command line in prompt.
	 */
	private char prompStymbol;

	/**
	 * Symbol which denotes more arguments in next line.
	 */
	private char moreSymbol;

	/**
	 * Path to the current directory.
	 */
	private Path currentDirectory;

	private Map<String,Object> sharedDataMap;
	
	public ShellEnviroment(Scanner sc, SortedMap<String, ShellCommand> commands, char multipleSymbol, char prompStymbol,
			char moreSymbol) {
		super();
		Objects.requireNonNull(sc);
		Objects.requireNonNull(commands);
		this.sc = sc;
		this.commands = commands;
		this.multipleSymbol = multipleSymbol;
		this.prompStymbol = prompStymbol;
		this.moreSymbol = moreSymbol;
		// get current directory path
		this.currentDirectory = Paths.get(System.getProperty("user.dir")).normalize();
		this.sharedDataMap = new HashMap<String, Object>();
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}
	
	
	/**
	 * {@inheritDoc}
	 * @throws ShellIOException if given path is not path to the existing directory.
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if(!path.isAbsolute()) {
			path = currentDirectory.resolve(path);
		}
		
		if (!Files.isDirectory(path))
			throw new ShellIOException("Given path is not path to existing directory.");
		
		currentDirectory = path;
	}

	@Override
	public Object getSharedData(String key) {
		return sharedDataMap.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedDataMap.put(key, value);
	}

	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.printf(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multipleSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multipleSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return prompStymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		prompStymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreSymbol = symbol;
	}

}
