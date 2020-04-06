package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Web worker which echos parameters provided in request through url into
 * context's output stream..
 * 
 * @author Filip Hustić
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		try {

			context.write("<html><body>");
			context.write("<style>");
			context.write("table, th, td {  border: 1px solid black; }");
			context.write("</style>");

			context.write("<h2>Parameters Tabl<h2>");
			context.write("<table>");

			context.write("<tr>");
			context.write("<th>Name</th>");
			context.write("<th>Value</th>");
			context.write("</tr>");

			context.getParameterNames().stream().forEach(name -> {
				try {
					String value = context.getParameter(name);

					context.write("<tr>");
					context.write("<td>" + name + "</td>");
					context.write("<td>" + value + "</td>");
					context.write("</tr>");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			context.write("</table>");
			context.write("</body> </html>");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}
