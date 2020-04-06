package hr.fer.zemris.java.custom.scripting.lexer;


import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementNull;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Class that represents Lexer. Lexer can work in two states.TAG_STATE and
 * OUTSIDE_OF_TAGS_STATE. While in TAG_STATE lexers working method is
 * {@link #getElementsInTag()} and while in OUTSIDE_OF_TAGS_STATE lexers working
 * method is {@link #getElementsOutsideOfTag()}.
 * 
 * @author Filip Hustić
 *
 */
public class SmartScriptLexer {

	/**
	 * Input String.
	 */
	private char[] data;
	/**
	 * Last produced token.
	 */
	private SmartScriptToken token;
	/**
	 * Index of first non tested character.
	 */
	private int currentIndex;

	/**
	 * State in which lexer is working. Can be TAG_STATE and OUTSIDE_OF_TAGS_STATE.
	 */
	private SmartScriptLexerState state;

	/**
	 * Constructor which takes in string that will be split into tokens.
	 * 
	 * @param text string to be split into tokens.
	 */
	public SmartScriptLexer(String text) {
		if (text == null)
			throw new NullPointerException();

		data = editForLexer(text);
		state = SmartScriptLexerState.OUTSIDE_OF_TAGS_STATE;
	}

	/**
	 * Method sets state in which lexer is working.
	 * 
	 * @param state in which lexer will be working.
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null)
			throw new NullPointerException();

		this.state = state;
	}

	/**
	 * Method returns next token that lexer produces. If lexer is in TAG_STATE
	 * {@link #getElementsInTag()} method is called for finding tokens. Else if
	 * lexer Is in OUTSIDE_OF_TAGS_STATE {@link #getElementsOutsideOfTag()} is
	 * called for finding tokens. If EOF occurs token that is returned is type of
	 * EOF. Also if you call this method after reaching EOF exception will be
	 * thrown.
	 * 
	 * @return next token.
	 * @throws SmartScriptParserException if there was excpetion in methods
	 *                                    {@link #getElementsInTag()} or
	 *                                    {@link #getElementsOutsideOfTag()}. Or if
	 *                                    EOF was reached in one of previous calls.
	 */
	public SmartScriptToken nextToken() {
		if (currentIndex > data.length)
			throw new LexerException("You have reached to the end of file.");

		// is this end of file
		if (currentIndex == data.length) {
			currentIndex++;
			return token = new SmartScriptToken(SmartScriptTokenType.EOF, new ElementNull());
		}

		// do not care bout spaces
		while(data[currentIndex] == ' ') {
			currentIndex++;
		}

		if (state == SmartScriptLexerState.TAG_STATE) {
			return token = getElementsInTag();
		} else {
			return token = getElementsOutsideOfTag();
		}
	}

	/**
	 * Method returns tokens that are outside of tag. Types of tokens can be STRING
	 * or START_OF_TAG if {$ occurs. One string contains of any symbol except \ and
	 * is bordered with white spaces . Inside of strings there can be escaping of {
	 * and \, every other escaping will throw exception.
	 * 
	 * @return next token that occurs outside of tags.
	 * @throws SmartScriptParserException if there was illegal escaping, or if
	 *                                    string is not closed and continues to the
	 *                                    end of the file.
	 */
	private SmartScriptToken getElementsOutsideOfTag() {
		// if next token is { and there are more tokens after and token after { is $ (
		// '{$' ) we are entering tag
		if (data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
			currentIndex += 2;// you read { and $
			return getTagName();
		}

		String line = "";
		while (currentIndex < data.length && data[currentIndex] != ' ') {
			if (data[currentIndex] == '\\') {
				line += "\\";
				incrementIndex(); // if there is EOF after \\ throw exception
				line += tryEscapeingOutisideOFTag();
			} else {
				line += data[currentIndex++];
			}
		}

		return new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(line));
	}

	/**
	 * Method that finds valid tag name if there is one. Tag name can be variable or
	 * sign = . If anything else is tag name exception will be thrown.
	 * 
	 * @return tag name in form of token. Token is type START_OF_TAG.
	 */
	private SmartScriptToken getTagName() {
		if (currentIndex == data.length)
			throw new SmartScriptParserException("There was expected tag name, but you reached EOF.");

		if (data[currentIndex] == ' ')
			incrementIndex();

		if (data[currentIndex] == '=') {
			incrementIndex(); // you read =
			return new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable("="));
		} else if (Character.isLetter(data[currentIndex])) {
			String line = "";
			while (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
				line += data[currentIndex];
				incrementIndex();
			}
			return new SmartScriptToken(SmartScriptTokenType.START_OF_TAG, new ElementVariable(line));
		} else {
			throw new SmartScriptParserException(
					"Threr was expected tag name in form of variable or =, but non was found.fs");
		}
	}

	/**
	 * Method finds next token in tag. Token which can be found are variable,
	 * number, string, function of operator. Variable starts with letter after which
	 * follows zero or more letters, numbers and underscores. Number can be
	 * represented in form of int or form of double with out scientific notation.
	 * String starts and ends with ", \ and " can be escaped in side of string (e.g.
	 * "escep \\ \""). Function starts with @ after which follows a letter and after
	 * than can follow zero or more letters, digits or underscores. Operator can be
	 * are + (plus), - (minus), (multiplication), / (division), ^ (power). If end of
	 * tag is found method returns token with type END_OF_TAG.
	 * 
	 * @return next token.
	 * @throws SmartScriptParserException if there was illegal try of escaping of f
	 *                                    there is symbol which does not fit the
	 *                                    rules of tokens.
	 */
	private SmartScriptToken getElementsInTag() {
		
		while(data[currentIndex]=='\n' || data[currentIndex]=='\t' || data[currentIndex] == '\r' | data[currentIndex]==' ') {
			incrementIndex();
		}
		
		
		// after $ there must be }.
		if (data[currentIndex] == '$') {
			incrementIndex();
			if (data[currentIndex] == '}') {
				currentIndex++;
				return new SmartScriptToken(SmartScriptTokenType.END_OF_TAG, new ElementNull());
			} else {
				throw new SmartScriptParserException("There is expected } after $ to end the tag. ( '$}' )");
			}
		}

		// is next up variable
		if (Character.isLetter(data[currentIndex])) {
			return getNextVariable();
			// if next up is string
		} else if (data[currentIndex] == '"') {
			return getNextString();
			// is next token digit or (is next '-' and there is more tokens after it and
			// next token is digit)
		} else if (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' && currentIndex + 1 < data.length
				&& Character.isDigit(data[currentIndex + 1]))) {
			return getNextNumber();
			// if next up is function
		} else if (data[currentIndex] == '@') {
			return getNextFunction();
		} else if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
				|| data[currentIndex] == '/' || data[currentIndex] == '^') {
			return getNextOperator();
		} else {
			throw new SmartScriptParserException("Invalid expression for inside of tag.");
		}

	}

	/**
	 * Method finds next variable while incrementing index.If after incrementation
	 * current index points to EOF exception is thrown.
	 * 
	 * @return next token which is function.
	 * @throws SmartScriptParserException if after variable follows EOF.
	 */
	private SmartScriptToken getNextVariable() {
		String line = "";

		while (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
			line += data[currentIndex];
			incrementIndex();
		}

		return new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable(line));
	}

	/**
	 * Method finds next String while incrementing index.If after incrementation
	 * current index points to EOF exception is thrown.
	 * 
	 * @return next token which is string.
	 * @throws SmartScriptParserException if after or in the middle of string
	 *                                    follows EOF.
	 */
	private SmartScriptToken getNextString() {
		String line = "\"";

		// because we have already read " into line
		incrementIndex();
		while (data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				line += "\\";
				incrementIndex();
				line += tryEscapeingInTag();
			} else {
				line += data[currentIndex];
				incrementIndex();
			}
		}
		line += "\"";// end qoute
		incrementIndex();

		return new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(line));
	}

	/**
	 * Method finds next number while incrementing index .If after incrementation
	 * current index points to EOF exception is thrown. Numbers can be represented
	 * in form of integer and in form of double with out scientific notation.
	 * 
	 * @return next token which is number.
	 * @throws SmartScriptParserException if after number follows EOF.
	 */
	private SmartScriptToken getNextNumber() {
		String line = "";

		if (data[currentIndex] == '-') {
			// read that '-' and increment currentIndex
			line = "-";
			incrementIndex();
		}

		boolean wasDot = false;// is this decimal number
		while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			line += data[currentIndex];
			if (data[currentIndex] == '.')
				wasDot = true; // if there was dot format it like double, else like int
			incrementIndex();
		}

		if (wasDot) {
			try {
				double number = Double.parseDouble(line);
				return new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantDouble(number));
			} catch (NumberFormatException ex) {
				throw new SmartScriptParserException("You formated number wrong.");
			}
		} else {
			try {
				int number = Integer.parseInt(line);
				return new SmartScriptToken(SmartScriptTokenType.NUMBER, new ElementConstantInteger(number));
			} catch (NumberFormatException ex) {
				throw new SmartScriptParserException("You formated number wrong.");
			}
		}
	}

	/**
	 * Method finds next function while incrementing index.If after incrementation
	 * current index points to EOF exception is thrown.
	 * 
	 * @return next token which is function.
	 * @throws SmartScriptParserException if after function follows EOF.
	 */
	private SmartScriptToken getNextFunction() {
		String line = "@";
		incrementIndex();// you read @

		if (!Character.isLetter(data[currentIndex]))
			throw new SmartScriptParserException("After sign @ there must be a letter for function to have its shape.");

		while (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_') {
			line += data[currentIndex];
			incrementIndex();
		}
		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(line));
	}

	/**
	 * Method finds next symbol that is operator and increments index. If after
	 * incrementation current index points to EOF exception is thrown.
	 * 
	 * @return next token which is operator.
	 * @throws SmartScriptParserException if after operator follows EOF.
	 */
	private SmartScriptToken getNextOperator() {
		SmartScriptToken token = new SmartScriptToken(SmartScriptTokenType.OPERATOR,
				new ElementOperator(String.valueOf(data[currentIndex])));
		incrementIndex();
		return token;
	}

	/**
	 * Method increments index and checks if after increment there was EOF, if there
	 * was exception is thrown. This is useful for increment in tags because there
	 * can not be EOF inside of tags.
	 * 
	 * @throws SmartScriptParserException if after increment currentIndex points to
	 *                                    EOF.
	 */
	private void incrementIndex() {
		currentIndex++;
		if (currentIndex == data.length)
			throw new SmartScriptParserException("You have reached the end of file but some sequence did not finish.");

	}

	/**
	 * Returns last generated token.
	 * 
	 * @return last generated token.
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Method removes unnecessary spaces in lines. Also it puts space in front of {
	 * if one is not escaped.
	 * 
	 * @param line from which spaces will be removed.
	 * @return array without extra spaces and with { separated from other symbols.
	 */
	private char[] editForLexer(String line) {

		// TODO bolji regex nac
		line = line.replaceAll("\\{", " \\{"); // everytime, that there is {, except \{ , put space in front of {
		line = line.replaceAll("\\\\ \\{", "\\\\\\{");
		line = line.replaceAll("  +", " ");// replace all multiple spaces with one space
		if (line.startsWith(" "))
			line = line.replaceFirst(" ", "");// replace first space with blank
		if (line.endsWith(" "))
			line = line.substring(0, line.length() - 1); // replace last space with blank

		return line.toCharArray();
	}

	/**
	 * Method tries to escape char outside of tag. Valid escaping is one of
	 * backslash \ or one of the curly bracket. All other escapings will end up with
	 * thrown exception
	 * 
	 * @return \ or {
	 * @throws SmartScriptParserException if escaping is done on some char that is
	 *                                    not \ or {.
	 */
	private char tryEscapeingOutisideOFTag() {

		if (data[currentIndex] == '\\') {
			return data[currentIndex++];
		} else if ((data[currentIndex] == '{')) {
			return data[currentIndex++];
		} else {
			throw new SmartScriptParserException("You tried to escape(\\) something that is not an { or another \\.");
		}
	}

	/**
	 * Method tries to escape char inside of tags. Valid escaping are those of
	 * quotation marks and of another backslash.eq \1a\2 is word 1a2. a\"b is a"1.
	 * 
	 * @return escape digit or \
	 * @throws LexerException if there is noting in line after \\ or if esacping is
	 *                        done one anything else than " or \.
	 */
	private char tryEscapeingInTag() {
		if (data[currentIndex] == '\\') {
			return data[currentIndex++];
		} else if ((data[currentIndex] == '"')) {
			return data[currentIndex++];
		} else if ((data[currentIndex] == 'n' || data[currentIndex] == 'r' || data[currentIndex] == 't' ) ) {
			return data[currentIndex++];
		} else {
			throw new SmartScriptParserException("You tried to escape(\\) something that is not an \"  or another \\");
		}
	}

}
