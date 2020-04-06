package hr.fer.zemris.java.webappdb.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * Servler which gets PollOptions of Poll with id provided through parameters
 * and sends it to jsp file for rendering.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/glasanje")
public class VotingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.parseLong(req.getParameter("poolID"));
		ArrayList<PollOption> options = DAOProvider.getDao().getPoolOptions(id);

		req.setAttribute("options", options);
		req.setAttribute("pollId", id);
		req.getRequestDispatcher("/WEB-INF/pages/poolOptions.jsp").forward(req, resp);
	}

}
