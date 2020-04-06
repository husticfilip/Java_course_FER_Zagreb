package hr.fer.zemris.java.webapp.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which processes one vote.
 * 
 * @author Filip Hustić
 *
 */
public class VotingVoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String RELATIVE_TXT_PATH = "/WEB-INF/votingResults.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");
		File srcFile = new File(req.getServletContext().getRealPath(RELATIVE_TXT_PATH));
		TreeMap<String, Integer> resultMap = new TreeMap<String, Integer>();

		VotingUtilities.readCurrentResults(resultMap, req);

		Integer selectedResult = resultMap.get(id);
		selectedResult = selectedResult == null ? 1 : selectedResult + 1;
		resultMap.put(id, selectedResult);

		writeResults(resultMap, srcFile);

		req.getRequestDispatcher("voting-results").forward(req, resp);
	}

	/**
	 * Method writes result of voting into provided file.
	 * 
	 * @param resultMap map of results of voting.
	 * @param srcFile   file in which results will be written.
	 */
	private void writeResults(TreeMap<String, Integer> resultMap, File srcFile) {

		try (BufferedWriter writter = new BufferedWriter(new FileWriter(srcFile))) {

			for (Entry<String, Integer> entry : resultMap.entrySet()) {
				StringBuilder builder = new StringBuilder();
				builder.append(entry.getKey());
				builder.append("\t");
				builder.append(entry.getValue());
				builder.append("\n");
				writter.write(builder.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
