package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which sets background color of index2 page.
 * 
 * @author Filip Hustić
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Pattern colorPattern = Pattern.compile("[ABCDEFabcdef0-9]{6}");

		String color = context.getParameter("bgcolor");
		if (color != null) {
			color.trim();
			if (colorPattern.matcher(color).find()) {
				context.setPersistentParameter("bgcolor", color);
			}
		}

		context.getiDispatcher().dispatchRequest("/index2.html");
	}

}
