package hr.fer.zemris.java.webappdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;

/**
 * Servlet which registers one vote of PollOption which id is provided through
 * parameters. Also id of Poll in which PollOption participates is provided
 * through parameters.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/registerVote")
public class RegisterVoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollId = Long.parseLong(req.getParameter("pollId"));
		Long optionId = Long.parseLong(req.getParameter("optionId"));

		DAOProvider.getDao().registerVote(pollId, optionId);
		req.getRequestDispatcher("votingResults?pollId=" + pollId).forward(req, resp);
	}

}
