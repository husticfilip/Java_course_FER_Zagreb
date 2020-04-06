package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents one expression of the query.
 * 
 * @author Filip Hustić
 *
 */
public class ConditionalExpression {

	/**
	 * Getter of attribute in query expression.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String literal to be tested in query expression.
	 */
	private String stringLiteral;
	/**
	 * Comparison that takes place in query expression.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor.
	 * 
	 * @param fieldGetter        Getter of attribute in query expression.
	 * @param stringLiteral      String literal to be tested in query expression
	 * @param comparisonOperator Comparison that takes place in query expression.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
