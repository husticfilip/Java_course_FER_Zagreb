package hr.fer.zemris.java.webapp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.webapp.beans.BandBean;

/**
 * Class contains utility methods for voting servlets.
 * 
 * @author Filip Hustić
 *
 */
public class VotingUtilities {

	/**
	 * Path to the voting results.
	 */
	private static final String RESULTS_TXT_PATH = "/WEB-INF/votingResults.txt";
	/**
	 * Path to the band definition.
	 */
	private static final String BAND_DEFINITION_TXT_PATH = "WEB-INF/votingDefinition.txt";

	/**
	 * Method reads current results from result's txt.
	 * 
	 * @param resultMap map in which results will be saved.
	 * @param req       httpServletReques.
	 */
	public static void readCurrentResults(TreeMap<String, Integer> resultMap, HttpServletRequest req) {

		File srcFile = new File(req.getServletContext().getRealPath(RESULTS_TXT_PATH));

		try (BufferedReader reader = new BufferedReader(new FileReader(srcFile))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\t");

				if (parts.length == 2) {
					int result = 0;
					try {
						result = Integer.parseInt(parts[1]);
					} catch (NumberFormatException ex) {
					}

					resultMap.put(parts[0], result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method reads band definition from txt file.
	 * 
	 * @param bandMap map in which band information read from txt file will be
	 *                saved.
	 * @param req     httpServletReques.
	 */
	public static void readBandDefinition(TreeMap<String, BandBean> bandMap, HttpServletRequest req) {
		String fileName = req.getServletContext().getRealPath(BAND_DEFINITION_TXT_PATH);

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\t");
				if (parts.length == 3) {
					try {

						bandMap.put(parts[0], new BandBean(parts[0], parts[1], parts[2], 0));
					} catch (NumberFormatException ex) {
					}
				}
			}

		} catch (IOException ex) {
		}
	}

	/**
	 * Method creates pie chart of provided dataset.
	 * 
	 * @param dataset
	 * @param title   chart title.
	 * @return
	 */
	public static JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

}
