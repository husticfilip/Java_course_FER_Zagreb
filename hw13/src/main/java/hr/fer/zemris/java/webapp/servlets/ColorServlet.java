package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which sets picked color as background color.
 * @author Filip Hustić
 *
 */
public class ColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("color"));
		req.getRequestDispatcher("/colors").forward(req, resp);
	}

	
	
}
