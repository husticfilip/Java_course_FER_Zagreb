package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Sum worker which sums parameters provided in request url and calls context's
 * dispatcher to process calc.smscr smart.
 * 
 * @author Filip Hustić
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		String a = context.getParameter("a");
		String b = context.getParameter("b");

		a = a == null ? "1" : a;
		b = b == null ? "2" : b;

		int varA = parseToInt(a, 1);
		int varB = parseToInt(b, 2);

		int zbroj = varA + varB;

		context.setTemporaryParameter("zbroj", String.valueOf(zbroj));
		context.setTemporaryParameter("varA", String.valueOf(varA));
		context.setTemporaryParameter("varB", String.valueOf(varB));

		if (zbroj % 2 == 0) {
			context.setTemporaryParameter("imgName", "webroot/images/redSmoke.png");
		} else {
			context.setTemporaryParameter("imgName", "webroot/images/valdarke.png");
		}

		context.getiDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

	/**
	 * Parse string value into int or if change is not possible return default
	 * value.
	 * 
	 * @param value        to be parsed.
	 * @param defaultValue value returned if change can not be done.
	 * @return parsed value or default value.
	 */
	private int parseToInt(String value, int defaultValue) {

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}

	}

}
