package hr.fer.zemris.java.webappdb.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * Servlet which generates pie chart of data in Poll which id was provided
 * through parameters.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/pieChartServlet")
public class PieChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CHART_TITLE = "Poll results";
	private static final int CHART_WIDTH = 700;
	private static final int CHART_HEIGHT = 500;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollId = Long.parseLong(req.getParameter("pollId"));
		ArrayList<PollOption> options = DAOProvider.getDao().getPoolOptions(pollId);
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (PollOption option : options) {
			dataset.setValue(option.getOptionTitle(), option.getVotesCount());
		}

		JFreeChart chart = ChartFactory.createPieChart3D(CHART_TITLE, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		BufferedImage bim = chart.createBufferedImage(CHART_WIDTH, CHART_HEIGHT);
		byte[] image = ChartUtilities.encodeAsPNG(bim);

		resp.setContentType("image/png");
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);

	}

}
