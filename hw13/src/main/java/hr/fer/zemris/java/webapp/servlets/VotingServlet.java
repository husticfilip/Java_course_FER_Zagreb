package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapp.beans.BandBean;

/**
 * Method which processes values for voting.jsp.
 * 
 * @author Filip Hustić
 *
 */
public class VotingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TreeMap<String, BandBean> bandMap = new TreeMap<String, BandBean>();
		VotingUtilities.readBandDefinition(bandMap, req);

		req.setAttribute("bandMap", bandMap);
		req.getRequestDispatcher("WEB-INF/pages/voting.jsp").forward(req, resp);
	}

}
