package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer which splits input string into tokens.
 * 
 * @author Filip Hustić
 *
 */
public class Lexer {

	/**
	 * Input String.
	 */
	private char[] data;
	/**
	 * Current split token.
	 */
	private Token token;
	/**
	 * Index of first non tested character.
	 */
	private int currentIndex;

	/**
	 * State in which lexer is working. Can be basic and extended.
	 */
	private LexerState state;

	/**
	 * Constructor which takes in string that will be split into tokens.
	 * 
	 * @param text string to be split into tokens.
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();

		data = editForLexer(text);
		state = LexerState.BASIC;
	}

	/**
	 * Method sets state in which lexer is working.
	 * 
	 * @param state in which lexer will be working.
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException();

		this.state = state;
	}

	/**
	 * Method generates next token. Last token that can be generated is EOF, after
	 * it there is no more tokens to be generated exception is being thrown.
	 * 
	 * @return returns next token.
	 * @throws LexerException if there was mistake. If there was bad escaping(valid
	 *                        ones are for digits and backslash). If end of file has
	 *                        been reached before this call.
	 */
	public Token nextToken() {
		if (currentIndex > data.length)
			throw new LexerException("You have reached to the end of file.");

		// is this end of file
		if (currentIndex == data.length) {
			currentIndex++;
			return token = new Token(TokenType.EOF, null);
		}

		// do not care bout spaces
		if (data[currentIndex] == ' ') {
			currentIndex++;
		}
		// in which state we will work
		if (state == LexerState.BASIC)
			return token = basicState();
		else
			return token = extendedState();
	}

	/**
	 * Method in which lexer works in its basic state splitting text into words,
	 * numbers and symbols. Escaping is provided here. Escaping can be done on
	 * digits so they are interpreted into words and on backslash \.
	 * 
	 * @return returns next token.
	 * @throws LexerException if there was mistake. If there was bad escaping(valid
	 *                        ones are for digits and backslash). If end of file has
	 *                        been reached before this call.
	 */
	public Token basicState() {

		// is next up word or \\
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			return token = getNextWord();
		} else if (Character.isDigit(data[currentIndex])) {
			return token = getNextNumber();
		} else {
			char symbol = data[currentIndex++];
			return new Token(TokenType.SYMBOL, symbol);
		}
	}

	/**
	 * Method works when lexer is in extended state in which lexer
	 * considers word to be any combinations of letters, numbers and symbols
	 * which are between two spaces, or space and symbol #(eg.'1a he22o v2#' has 3 words;
	 * 1a,he22o and v2). 
	 * @return next word compossed of letters,digits and symbols(exception is symbol #)
	 * 		  if form of Token, or symbol # in form of Token
	 */
	public Token extendedState() {
		String line = "";

		if (data[currentIndex] == '#') {
			currentIndex++;
			return new Token(TokenType.SYMBOL, '#');
		} else {
			while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '#') {
				line += data[currentIndex++];
			}
			return new Token(TokenType.WORD, line);
		}
	}

	/**
	 * Method finds word that starts from currentIndex and returns it in the form of
	 * token. If there was mistake while escaping method throws exception.
	 * 
	 * @return next word in the form of Token.
	 * @throws LexerException if there was bad escaping in tryEscaping() method.
	 */
	public Token getNextWord() {
		String line = "";

		while ((currentIndex < data.length && Character.isLetter(data[currentIndex]))
				|| (currentIndex < data.length && data[currentIndex] == '\\')) {

			if (data[currentIndex] == '\\') {
				currentIndex++; // you read '\\'
				line += tryEscapeing();
			} else {
				line += data[currentIndex++];
			}
		}

		if (line.equals("\\"))
			throw new LexerException("Line consists of only spaces and \\");

		return new Token(TokenType.WORD, line);
	}

	/**
	 * Method finds number that starts from currentIndex and returns it in the form
	 * of token. If found number is out of boundaries of long the exception is
	 * thrown.
	 * 
	 * @return next number in form of Token.
	 * @throws LexerException If found number is out of boundaries of long the
	 *                        exception is thrown.
	 */
	public Token getNextNumber() {
		String line = "";

		while ((currentIndex < data.length && Character.isDigit(data[currentIndex]))) {
			line += data[currentIndex++];
		}

		try {
			long number = Long.parseLong(line);
			return new Token(TokenType.NUMBER, number);
		} catch (NumberFormatException ex) {
			throw new LexerException("Given number is out of boundaries for type long.");
		}
	}

	/**
	 * Method returns last generated token. It can return same token multiple times
	 * until nextToken() is not called.
	 * 
	 * @return last generated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method removes unnecessary symbols for lexer. Those are:'\r','\n', '\t', ' '.
	 * 
	 * @param line from which symbols will be removed.
	 * @return array without those symbols.
	 */
	private char[] editForLexer(String line) {
		line = line.replaceAll("\\s+"," ").trim();
		line = line.replaceAll("\r", "");
		line = line.replaceAll("\n", "");
		line = line.replace("\t", "");
		line = line.replace("  ", " ");

		return line.toCharArray();
	}

	/**
	 * Method tries to do escaping of the char. Valid escapings are those of number
	 * and of another backslash.eq \1a\2 is word 1a2. Also a\\\1 is a\1.
	 * 
	 * @return escape digit or \
	 * @throws LexerException if there is noting in line after \\ or if esacping is
	 *                        done one anything else than digit or \.
	 * 
	 */
	public char tryEscapeing() {

		if (currentIndex == data.length) {
			throw new LexerException("You tried to escape(\\) but after it there is only EOF.");
		} else if (data[currentIndex] == '\\') {
			currentIndex++;
			return '\\';
		} else if (Character.isDigit(data[currentIndex])) {
			return data[currentIndex++];
		} else {
			throw new LexerException("You tried to escape(\\) something that is not digit or another \\");
		}
	}

}
