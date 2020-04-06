package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Home worker which calls context's dispatcher to process home.smscr smart
 * script.
 * 
 * @author Filip Hustić
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		String bgcolor = context.getPersistentParameter("bgcolor");
		if (bgcolor != null) {
			context.setTemporaryParameter("background", bgcolor);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}

		context.getiDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
