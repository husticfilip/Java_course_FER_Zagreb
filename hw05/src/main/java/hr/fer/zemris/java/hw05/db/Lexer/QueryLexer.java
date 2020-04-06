package hr.fer.zemris.java.hw05.db.Lexer;

/**
 * Class which represents lexer of queries.
 * 
 * @author Filip Hustić
 *
 */
public class QueryLexer {

	/**
	 * Query divided into char array.
	 */
	char[] data;
	/**
	 * Current index lexer points to in data[].
	 */
	int currentIndex;
	/**
	 * Mode in which lexer works.
	 */
	QueryLexerModes mode;

	/**
	 * Constructor which takes in query in form of String and sets mode of work to
	 * ATRIBUT_MODE.
	 * 
	 * @param body query in form of string.
	 */
	public QueryLexer(String body) {
		data = body.trim().replaceAll("\\s+", " ").toCharArray();
		mode = QueryLexerModes.ATRIBUT_MODE;
		currentIndex = 0;
	}

	/**
	 * Method returns next token.
	 * <p>
	 * Lexer can work in 4 different modes.
	 * <p>
	 * <p>
	 * In ATRIBUT_MODE method calls {@link #nextAtributeAndOperation()} to get next
	 * token.
	 * <p>
	 * <p>
	 * In OPERATOR_LITERAL_MODE method calls {@link #nextStringLiteral()} to get next
	 * token.
	 * <p>
	 * <p>
	 * In LIKE_LITERAL_MODE method calls {@link #nextLikeStringLiteral()} to get next
	 * token.
	 * <p>
	 * <p>In AND mode method calls {@link #tryAnd()}.<p>
	 * @return
	 */
	public QueryToken nextToken() {
		if (currentIndex >= data.length)
			throw new QueryParserException("End of body has been reached.");

		if (data[currentIndex] == ' ')
			currentIndex++;

		if (mode == QueryLexerModes.ATRIBUT_MODE)
			return nextAtributeAndOperation();
		if (mode == QueryLexerModes.OPERATOR_LITERAL_MODE)
			return nextStringLiteral();
		if (mode == QueryLexerModes.LIKE_LITERAL_MODE)
			return nextLikeStringLiteral();
		else
			return tryAnd();
	}

	public boolean hasNextToken() {
		return currentIndex < data.length;
	}

	/**
	 * Method checks if there is and next up.
	 * 
	 * @return AND token if there is and up next.
	 * @throws QueryParserException if there was not and up next.
	 */
	private QueryToken tryAnd() {
		String possibleAnd = nextAtribute();

		if (!possibleAnd.toLowerCase().equals("and"))
			throw new QueryParserException("And expected");

		return new QueryToken(null, QueryTokenType.AND);
	}

	/**
	 * Method returns next StringLiteral of operator LIKE.
	 * <p>
	 * Literal is made up of letters or digits and can contain one wild card *.
	 * <p>
	 * 
	 * @return next string literal.
	 * @throws QueryParserException if literal does not start with ". Or if literal
	 *                              consists of something that is of letter or digit
	 *                              *. Or if * is present more than once. Or if end
	 *                              of query has been reached.
	 */
	private QueryToken nextLikeStringLiteral() {
		if (data[currentIndex] != '"')
			throw new QueryParserException("String literal expected.");

		incrementIndex();
		int wildCardCounter = 0;
		StringBuilder literal = new StringBuilder();
		while (data[currentIndex] != '"') {
			if (!Character.isLetterOrDigit(data[currentIndex]) && data[currentIndex] != '*')
				throw new QueryParserException("String not closed.");

			if (data[currentIndex] == '*')
				wildCardCounter++;

			literal.append(data[currentIndex]);
			incrementIndex();
		}
		// you read closing "
		currentIndex++;

		if (wildCardCounter > 1)
			throw new QueryParserException("Wild card operator used more than once.");

		return new QueryToken(literal.toString(), QueryTokenType.LITERAL);
	}

	/**
	 * Method returns next StringLiteral which is part of every operator except
	 * LIKE.
	 * <p>
	 * Literal is made up of letters or digits.
	 * <p>
	 * 
	 * @return next string literal.
	 * @throws QueryParserException if literal does not start with ". Or if literal
	 *                              consists of something that is ot letter or
	 *                              digit. Or if end of query has been reached.
	 */
	private QueryToken nextStringLiteral() {
		if (data[currentIndex] != '"')
			throw new QueryParserException("String literal expected.");

		incrementIndex();
		StringBuilder literal = new StringBuilder();
		while (data[currentIndex] != '"') {
			if (!Character.isLetterOrDigit(data[currentIndex]))
				throw new QueryParserException("String not closed.");
			literal.append(data[currentIndex]);
			incrementIndex();
		}
		// you read closing "
		currentIndex++;

		return new QueryToken(literal.toString(), QueryTokenType.LITERAL);
	}

	/**
	 * Method gets next attribute and operation.
	 * <p>
	 * Operation is set in token as tokenType. Operator can be: = != < <= > >= LIKE
	 * <p>
	 * <p>
	 * Attribute is set in token as tokenValue. Argument can be:fistName, lastName
	 * and jmbag.
	 * <p>
	 * <p>
	 * For getting attribute method calls {@link #nextAtribute()}. And operation
	 * method gets on its own.
	 * <p>
	 * 
	 * @return next token which holds attribute and operation.
	 * @throws QueryParserException if end of query has been reached. Or if given
	 *                              attribute is not valid one. Or if operation is
	 *                              not valid one.
	 */
	private QueryToken nextAtributeAndOperation() {
		String atribute = nextAtribute();
		if (!atribute.equals("jmbag") && !atribute.equals("lastName") && !atribute.equals("firstName"))
			throw new QueryParserException("Atribute is not valid.");

		if (data[currentIndex] == ' ')
			incrementIndex();

		if (data[currentIndex] == '>') {
			incrementIndex();

			if (data[currentIndex] == '=') {
				incrementIndex();
				return new QueryToken(atribute, QueryTokenType.GREATER_OR_EQUAL);
			} else {
				return new QueryToken(atribute, QueryTokenType.GREATER);
			}

		} else if (data[currentIndex] == '<') {
			incrementIndex();

			if (data[currentIndex] == '=') {
				incrementIndex();
				return new QueryToken(atribute, QueryTokenType.LESS_OR_EQUAL);
			} else {
				return new QueryToken(atribute, QueryTokenType.LESS);
			}

		} else if (data[currentIndex] == '!') {
			incrementIndex();

			if (data[currentIndex] == '=') {
				incrementIndex();
				return new QueryToken(atribute, QueryTokenType.NOT_EQUAL);
			}

		} else if (data[currentIndex] == '=') {
			incrementIndex();

			return new QueryToken(atribute, QueryTokenType.EQUAL);

		} else if (data[currentIndex] == 'L') {
			String possibleLike = nextAtribute();

			if (possibleLike.equals("LIKE"))
				return new QueryToken(atribute, QueryTokenType.LIKE);

		}

		throw new QueryParserException("Invalid operation.");
	}

	/**
	 * Method returns nextAtribute.
	 * <p>
	 * Attribute consists of letters
	 * <p>
	 * 
	 * @return next attribute.
	 * @throws QueryParserException if end of query has been reached.
	 */
	private String nextAtribute() {
		StringBuilder builder = new StringBuilder();
		while (Character.isLetter(data[currentIndex])) {
			builder.append(data[currentIndex]);
			incrementIndex();
		}
		return builder.toString();
	}

	/**
	 * Method increments currentIndex. If end of query has been reached exception is
	 * being thrown.
	 * 
	 * @throws QueryParserException if end of query has been reached.
	 */
	private void incrementIndex() {
		if (currentIndex == data.length - 1)
			throw new QueryParserException("End of body has been reached.");

		currentIndex++;
	}

	/**
	 * Method sets work mode in which lexer works.
	 * 
	 * @param mode work mode of lexer.
	 */
	public void setMode(QueryLexerModes mode) {
		this.mode = mode;
	}

}
