package hr.fer.zemris.java.webserver;

/**
 * Interface represents dispatcher which handles server and web worker's calls
 * and dispatches wanted output to client.
 * 
 * @author Filip Hustić
 *
 */
public interface IDispatcher {

	/**
	 * Method dispatches call with given urlPath, gets wanted output bytes and sends
	 * it to the client in form of RCRequest.
	 * 
	 * @param urlPath path of desired web resource.
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
