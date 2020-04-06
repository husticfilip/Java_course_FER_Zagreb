package hr.fer.zemris.java.hw06.shell.Lexer;

import java.util.Objects;

/**
 * Lexer used for getting paths and arguments of shell commands.
 * 
 * @author Filip Hustić
 *
 */
public class ShellLexer {

	/**
	 * Mode in which lexer works.
	 */
	private LexerModes mode;
	/**
	 * Data from which lexer forms tokens.
	 */
	private char[] data;
	/**
	 * Index at what lexer currently point to.
	 */
	private int currentIndex;

	/**
	 * Constructor which takes in mode in which lexer works and data through which
	 * lexer will look for tokens.
	 * 
	 * @param mode in which lexer works.
	 * @param body data through which lexer will look for tokens.
	 */
	public ShellLexer(LexerModes mode, String body) {
		Objects.requireNonNull(body);

		this.mode = mode;
		this.data = body.trim().toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method returns next token.
	 * <p>
	 * Token can be path or name of argument.
	 * <p>
	 * 
	 * @return
	 * @return next path.
	 * @throws ShellLexerException if "" form is used and end of data has been
	 *                             reached or if path ends with \\ : ? * \" < > |.
	 */
	public ShellToken nextToken() {
		if (currentIndex == data.length)
			throw new ShellLexerException("End of command has been reached");

		while (data[currentIndex] == ' ')
			currentIndex++;

		if (mode == LexerModes.PATH_MODE)
			return nextPath();
		else if(mode == LexerModes.NAME_MODE)
			return nextName();
		else 
			return nextPattern();

	}

	/**
	 * Method checks if lexer has more data to go through.
	 * 
	 * @return true if lexer has more data to go through, false otherwise.
	 */
	public boolean hasNext() {
		return currentIndex != data.length;
	}

	/**
	 * Method find next path.
	 * <p>
	 * Path can be in form of c:\a\bcd with no spaces in path.
	 * <p>
	 * <p>
	 * Or in form "c:a\b c d\e" where there can be spaces in path, this form is used
	 * with "";
	 * <p>
	 * 
	 * @return next path.
	 * @throws ShellLexerException if "" form is used and end of data has been
	 *                             reached or if path ends with \\ : ? * \" < > |.
	 */
	private ShellToken nextPath() {
		StringBuilder path = new StringBuilder();

		if (data[currentIndex] == '"') {
			// you have read first "
			incrementIndex();

			while (data[currentIndex] != '"') {

				if (data[currentIndex] == '\\' && currentIndex != data.length - 2) {
					incrementIndex();
					path.append(escape());
				} else {
					path.append(data[currentIndex]);
				}
				incrementIndex();
			}
			// you have read last "
			currentIndex++;
		} else {
			while (currentIndex != data.length && data[currentIndex] != ' ') {
				path.append(data[currentIndex]);
				currentIndex++;
			}
		}

		// trim for qoutes eq: " " c:abc a \ a " " -> ""c:abc a \ a""
		String pathString = path.toString().trim().replaceAll(" +\\\\", "\\\\");
		char lastChar = pathString.charAt(pathString.length() - 1);
		if (lastChar == '\\' || lastChar == ':' || lastChar == '?' || lastChar == '*' || lastChar == '"'
				|| lastChar == '<' || lastChar == '>' || lastChar == '|')
			throw new ShellLexerException("Path can not end with \\ : ? * \" < > |");

		return new ShellToken(pathString, ShellTokenType.PATH);
	}

	/**
	 * Method returns next argument name.
	 * 
	 * @return next argument name.
	 */
	private ShellToken nextName() {
		StringBuilder name = new StringBuilder();
		while (currentIndex != data.length && data[currentIndex] != ' ') {
			name.append(data[currentIndex]);
			currentIndex++;
		}
		return new ShellToken(name.toString(), ShellTokenType.NAME);
	}

	/**
	 * Method finds next pattern.
	 * @return next pattern.
	 */
	private ShellToken nextPattern() {
		StringBuilder pattern = new StringBuilder();

		if (data[currentIndex] != '"') {
			return nextName();
		} else {
			// you have read first "
			incrementIndex();

			while (data[currentIndex] != '"') {

				if (data[currentIndex] == '\\' && currentIndex != data.length - 2) {
					incrementIndex();
					pattern.append(escape());
				} else {
					pattern.append(data[currentIndex]);
				}
				incrementIndex();
			}
			// you have read last "
			currentIndex++;
		}
		return new ShellToken(pattern.toString(), ShellTokenType.PATTTERN);
	}

	/**
	 * Method increments index and checks if end of data has been reached.
	 * 
	 * @throws ShellLexerException if end of data has been reached.
	 */
	private void incrementIndex() {
		currentIndex++;
		if (currentIndex == data.length)
			throw new ShellLexerException("End of command has been reached");
	}

	/**
	 * Method used to escape symbols.
	 * <p>
	 * Symbols which can be escaped are \ and "
	 * <p>
	 * <p>
	 * If there was no escaping \ concatenated with next symbol is returned
	 * <p>
	 * 
	 * @return escaped symbol or \\ and next symbol if there was no escaping.
	 */
	private String escape() {
		if (data[currentIndex] == '\\')
			return "\\";
		else if (data[currentIndex] == '"')
			return "\"";
		else
			return "\\" + data[currentIndex];
	}

	/**
	 * 
	 * @param mode in which lexer works.
	 */
	public void setMode(LexerModes mode) {
		this.mode = mode;
	}

}
