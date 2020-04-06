package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.Lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.Lexer.QueryLexerModes;
import hr.fer.zemris.java.hw05.db.Lexer.QueryParserException;
import hr.fer.zemris.java.hw05.db.Lexer.QueryToken;
import hr.fer.zemris.java.hw05.db.Lexer.QueryTokenType;

/**
 * Class that parses input query.
 * 
 * @author Filip Hustić
 *
 */
public class QueryParser {

	/**
	 * List of query expressions.
	 */
	ArrayList<ConditionalExpression> expressions;
	/**
	 * Lexer used to get tokens.
	 */
	QueryLexer lexer;

	public QueryParser(String query) {
		expressions = new ArrayList<ConditionalExpression>();
		lexer = new QueryLexer(query);
		parse();
	}

	/**
	 * Class questions if @this query was direct query.
	 * <p>
	 * Query is direct if it is done on jmbag and if operator is =.
	 * <p>
	 * 
	 * @return true if query is direct, false otherwise.
	 */
	public boolean isDirectQuery() {
		if (expressions.size() != 1) {
			return false;
		} else {
			ConditionalExpression expression = expressions.get(0);
			return expression.getComparisonOperator().equals(ComparisonOperators.EQUALS)
					&& expression.getFieldGetter().equals(FieldValueGetters.JMBAG);
		}
	}

	/**
	 * If query was direct method returns queried jmbag.
	 * 
	 * @return if query was direct method returns queried jmbag.
	 * @throws QueryParserException if query is not direct.
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery())
			throw new QueryParserException("Query was not direct.");
		return expressions.get(0).getStringLiteral();
	}

	/**
	 * Method returns list of expressions of @this query.
	 * 
	 * @return list of expressions of @this query.
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Method parses input query.
	 * <p>
	 * Valid exspression is one contains: argument operator literal
	 * <p>
	 * <p>
	 * Where argument can be:fistName, lastName and jmbag.
	 * <p>
	 * <p>
	 * Operator can be: = != < <= > >= LIKE
	 * <p>
	 * <p>
	 * String literal is literal that contains letters and digits. In case of LIKE
	 * it can hold one wild card(*).
	 * <p>
	 * <p>
	 * Query can have multiple of these expressions. Every expression is separated
	 * by and.
	 * <p>
	 * 
	 * @throws QueryParserException if query was not in the right format.
	 */
	private void parse() {

		while (true) {
			// First token holds attribute and operator
			QueryToken token = getToken();
			IFieldValueGetter fieldGetter = determineFieldValueGetter(token);
			IComparisonOperator operator = determineComparisonOperator(token);

			// Second token holds string literal
			if (token.type == QueryTokenType.LIKE)
				lexer.setMode(QueryLexerModes.LIKE_LITERAL_MODE);
			else
				lexer.setMode(QueryLexerModes.OPERATOR_LITERAL_MODE);

			token = getToken();
			String literal = determineLiteral(token);

			expressions.add(new ConditionalExpression(fieldGetter, literal, operator));
			// if there is no and after this expression
			if (!lexer.hasNextToken())
				return;
			// read and
			lexer.setMode(QueryLexerModes.AND_MODE);
			token = lexer.nextToken();
			if (token.type != QueryTokenType.AND)
				throw new QueryParserException("Between two expressions must be and.");

			lexer.setMode(QueryLexerModes.ATRIBUT_MODE);
		}

	}

	/**
	 * Helper method which checks if token is Literal type.
	 * 
	 * @param token to be check.
	 * @return string literal of the token.
	 * @throws QueryParserException if token is not of type Literal.
	 */
	private String determineLiteral(QueryToken token) {
		if (token.type != QueryTokenType.LITERAL)
			throw new QueryParserException("String literal expected.");
		return token.value;
	}

	/**
	 * Helper method which returns IFieldValueGetter that given token represents.
	 * 
	 * @param token
	 * @return IFieldValueGetter that given token represents.
	 * @throws QueryParserException if token value is not jmbag, firstName or
	 *                              lastName.
	 */
	private IFieldValueGetter determineFieldValueGetter(QueryToken token) {
		if (token.value.equals("jmbag"))
			return FieldValueGetters.JMBAG;
		else if (token.value.equals("lastName"))
			return FieldValueGetters.LAST_NAME;
		else if (token.value.equals("firstName"))
			return FieldValueGetters.FIRST_NAME;
		else
			throw new QueryParserException("Invalid atribute name");
	}

	/**
	 * Helper method which determines what type of operator token represents.
	 * 
	 * @param token
	 * @return type of operator token represents.
	 * @throws QueryParserException if token is not of one of the operation type.
	 */
	private IComparisonOperator determineComparisonOperator(QueryToken token) {

		if (token.type == QueryTokenType.GREATER)
			return ComparisonOperators.GREATER;
		else if (token.type == QueryTokenType.GREATER_OR_EQUAL)
			return ComparisonOperators.GREATER_OR_EQUALS;
		else if (token.type == QueryTokenType.LESS)
			return ComparisonOperators.LESS;
		else if (token.type == QueryTokenType.LESS_OR_EQUAL)
			return ComparisonOperators.LESS_OR_EQUALS;
		else if (token.type == QueryTokenType.EQUAL)
			return ComparisonOperators.EQUALS;
		else if (token.type == QueryTokenType.NOT_EQUAL)
			return ComparisonOperators.NOT_EQUALS;
		else if (token.type == QueryTokenType.LIKE)
			return ComparisonOperators.LIKE;
		else
			throw new QueryParserException("Invalid operator.");

	}

	/**
	 * Method which calls lexer to give it next token.
	 * 
	 * @return next token.
	 * @throws QueryParserException if there was error while getting next token.
	 */
	private QueryToken getToken() {
		try {
			return lexer.nextToken();
		} catch (QueryParserException ex) {
			throw new QueryParserException("Invalid exspresion.");
		}
	}

}
