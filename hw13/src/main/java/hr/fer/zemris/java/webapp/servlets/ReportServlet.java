package hr.fer.zemris.java.webapp.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet which writes simple report in form of pie chart. Pie chart is saved
 * in form of png.
 * 
 * @author Filip Hustić
 *
 */
public class ReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Title of chart.
	 */
	private static final String CHART_TITLE = "Os usage";
	/**
	 * Width of chart.
	 */
	private static final int CHART_WIDTH = 700;
	/**
	 * Height of chart.
	 */
	private static final int CHART_HEIGHT = 400;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PieDataset dataset = crateDataset();
		JFreeChart chart = VotingUtilities.createChart(dataset, CHART_TITLE);

		resp.setContentType("image/png");

		final File file1 = new File(getServletContext().getRealPath(".") + "/images/piecharts/osChart.png");
		ChartUtilities.saveChartAsPNG(file1, chart, CHART_WIDTH, CHART_HEIGHT);

		req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
		;
	}

	/**
	 * Method creates dataset.
	 * 
	 * @return dataset.
	 */
	private PieDataset crateDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		dataset.setValue("Linux", 70);
		dataset.setValue("Mac", 50);
		dataset.setValue("Windows", 100);

		return dataset;
	}

}
