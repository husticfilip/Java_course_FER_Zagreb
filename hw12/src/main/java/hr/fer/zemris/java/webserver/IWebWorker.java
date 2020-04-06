package hr.fer.zemris.java.webserver;

/**
 * Interface represents web worker which processes request call.
 * 
 * @author Filip Hustić
 *
 */
public interface IWebWorker {

	/**
	 * Method processes request and calls context's method for dispatching resources
	 * to client.
	 * 
	 * @param context dispatcher class.
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
