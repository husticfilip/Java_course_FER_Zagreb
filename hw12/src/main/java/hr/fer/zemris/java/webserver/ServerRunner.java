package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Main class which starts smarthttpserver.
 * @author Filip Hustić
 *
 */
public class ServerRunner {


	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");
		server.start();
	}

}
