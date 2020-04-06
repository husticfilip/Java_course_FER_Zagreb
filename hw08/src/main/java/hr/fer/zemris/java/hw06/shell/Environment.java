package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface which holds methods for communication between shell and user.
 * 
 * @author Filip Hustić
 *
 */
public interface Environment {

	/**
	 * Methods reads line from user input.
	 * 
	 * @return read line from user input.
	 * @throws ShellIOException if there was exception while reading.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method writes given text to user output.
	 * 
	 * @param text which will be written to user output.
	 * @throws ShellIOException if there was exception while writing.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method writes given line of text to user output.
	 * 
	 * @param text line which will be written to user output.
	 * @throws ShellIOException if there was exception while writing.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method gives list of all supported commands. Returned map is unchangeable.
	 * 
	 * @return list of supported commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * @return symbol which denotes multiple lines.
	 */
	Character getMultilineSymbol();

	/**
	 * 
	 * @param symbol which denotes multiple lines.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * 
	 * @return symbol which denotes beginning of command.
	 */
	Character getPromptSymbol();

	/**
	 * 
	 * @param symbol which denotes beginning of command.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * 
	 * @return symbol which denotes commands which go through multiple lines.
	 */
	Character getMorelinesSymbol();

	/**
	 * 
	 * @param symbol which denotes commands which go through multiple lines
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Method gets path to the current working directory.
	 * 
	 * @return path to the current working directory.
	 */
	Path getCurrentDirectory();

	/**
	 * Method sets given path as current working directory.
	 * 
	 * @param path to the new working directory.
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Method returns shared data between two or more methods.
	 * <p>
	 * Shared data is stored in map.
	 * <p>
	 * 
	 * @param key of shared data.
	 * @return shared data of given key.
	 */
	Object getSharedData(String key);

	/**
	 * Method sets new value in shared data map.
	 * 
	 * @param key   of new shared data.
	 * @param value of new shared data.
	 */
	void setSharedData(String key, Object value);

}
