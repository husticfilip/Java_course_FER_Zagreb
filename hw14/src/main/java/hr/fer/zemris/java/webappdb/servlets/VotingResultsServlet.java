package hr.fer.zemris.java.webappdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * Servlet which gets PollOptions of Poll which id is provided through
 * parameters and sends it to jsp file for rendering.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/votingResults")
public class VotingResultsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUMBER_OF_TOP_BANDS = 3;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollId = Long.parseLong(req.getParameter("pollId"));
		List<PollOption> options = DAOProvider.getDao().getPoolOptions(pollId);
		List<PollOption> topOptions = getTopOptions(options);

		req.setAttribute("options", options);
		req.setAttribute("poolId", pollId);
		req.setAttribute("topOptions", topOptions);

		req.getRequestDispatcher("/WEB-INF/pages/votingResult.jsp").forward(req, resp);
	}

	private List<PollOption> getTopOptions(List<PollOption> options) {
		options.sort((o1, o2) -> Integer.compare(o2.getVotesCount(), o1.getVotesCount()));

		int num_of_top_bands = NUMBER_OF_TOP_BANDS < options.size() ? NUMBER_OF_TOP_BANDS : options.size();

		return options.subList(0, num_of_top_bands);
	}

}
