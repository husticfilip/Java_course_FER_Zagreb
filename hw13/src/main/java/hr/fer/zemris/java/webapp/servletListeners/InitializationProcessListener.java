package hr.fer.zemris.java.webapp.servletListeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializationProcessListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long startUpTime = System.currentTimeMillis();
		//time when server started.
		sce.getServletContext().setAttribute("startUpTime", startUpTime);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
