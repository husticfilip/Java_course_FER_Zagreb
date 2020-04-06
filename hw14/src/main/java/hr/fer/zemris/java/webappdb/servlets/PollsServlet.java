package hr.fer.zemris.java.webappdb.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;
import hr.fer.zemris.java.webappdb.models.Poll;

/**
 * Servlet which gets all polls and sends it to jsp file for rendering.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Poll> polls = DAOProvider.getDao().getPools();

		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
