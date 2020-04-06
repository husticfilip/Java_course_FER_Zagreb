package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class which takes in list of ConditionalExspressions and filters the
 * studentRecord according to those expressions. If ConditionalOperator in each
 * of those expressions returns true record is valid for those expressions.
 * 
 * @author Filip Hustić
 *
 */
public class QueryFilter implements IFilter {

	List<ConditionalExpression> expressions;

	/**
	 * Constructor.
	 * 
	 * @param expressions list of query expressions.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		super();
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			IFieldValueGetter fieldGetter = expression.getFieldGetter();
			IComparisonOperator operator = expression.getComparisonOperator();
			String literal = expression.getStringLiteral();

			if (!operator.satisfied(fieldGetter.get(record), literal))
				return false;
		}
		return true;
	}

}
