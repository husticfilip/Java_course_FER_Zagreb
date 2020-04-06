package hr.fer.zemris.java.webapp.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.webapp.beans.BandBean;

/**
 * Servlet processes values for votingResult.jsp.
 * 
 * @author Filip Hustić
 *
 */
public class VotingResultsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Relative path where result chart will be saved.
	 */
	private static final String PIE_CHART_DEST = "/images/piecharts/resultChart.png";
	/**
	 * Width of chart.
	 */
	private static final int CHART_WIDTH = 700;
	/**
	 * Height of chart.
	 */
	private static final int CHART_HEIGHT = 500;
	/**
	 * Relative path where result excel will be saved.
	 */
	private static final String EXCEL_DEST = "/files/excels/resultsExcel.xls";
	/**
	 * Number of top bands which songs will be linked in votingResult.jsp
	 */
	private static final int NUMBER_OF_TOP_BAND_TO_SHOW_SONGS = 2;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TreeMap<String, BandBean> bandMap = new TreeMap<String, BandBean>();
		TreeMap<String, Integer> resultMap = new TreeMap<String, Integer>();

		VotingUtilities.readBandDefinition(bandMap, req);
		VotingUtilities.readCurrentResults(resultMap, req);

		// sorted by number of wotes
		TreeSet<BandBean> sortedBands = new TreeSet<BandBean>((o1, o2) -> {
			int res = Integer.compare(o2.getNumOfVotes(), o1.getNumOfVotes());
			return res == 0 ? o2.getName().compareTo(o1.getName()) : res;
		});

		// TreeSet<BandBean> sortedBands = new TreeSet<BandBean>(
		// (o1, o2) -> Integer.compare(o2.getNumOfVotes(), o1.getNumOfVotes()));

		for (Entry<String, BandBean> entry : bandMap.entrySet()) {
			String key = entry.getKey();

			if (resultMap.containsKey(key)) {
				entry.getValue().setNumOfVotes(resultMap.get(key));
			}
			sortedBands.add(entry.getValue());
		}
		req.setAttribute("bandMap", bandMap);

		createChart(bandMap);
		writeExcel(bandMap);

		ArrayList<BandBean> topBands = getTopBands(sortedBands);
		req.setAttribute("topBands", topBands);

		req.getRequestDispatcher("WEB-INF/pages/votingResult.jsp").forward(req, resp);
	}

	/**
	 * Method creates chart of voting results.
	 * 
	 * @param bandMap map which contains BandBean's.
	 */
	private void createChart(TreeMap<String, BandBean> bandMap) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Entry<String, BandBean> entry : bandMap.entrySet()) {
			dataset.setValue(entry.getValue().getName(), entry.getValue().getNumOfVotes());
		}

		JFreeChart chart = VotingUtilities.createChart(dataset, "Favorite band");
		final File file1 = new File(getServletContext().getRealPath(".") + PIE_CHART_DEST);

		try {
			ChartUtilities.saveChartAsPNG(file1, chart, CHART_WIDTH, CHART_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to write results into excel.
	 * 
	 * @param bandMap bandMap map which contains BandBean's.
	 */
	private void writeExcel(TreeMap<String, BandBean> bandMap) {
		@SuppressWarnings("resource")
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Results");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("Band");
		row.createCell(1).setCellValue("Result");

		int rowNum = 1;
		for (Entry<String, BandBean> entry : bandMap.entrySet()) {
			row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(entry.getValue().getName());
			row.createCell(1).setCellValue(entry.getValue().getNumOfVotes());
			rowNum++;
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(getServletContext().getRealPath(".") + EXCEL_DEST);
			hwb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method returns top bands determined by voting.
	 * 
	 * @param bandSet set of BandBean's.
	 * @return top bands.
	 */
	private ArrayList<BandBean> getTopBands(TreeSet<BandBean> bandSet) {
		ArrayList<BandBean> topBands = new ArrayList<BandBean>();

		int i = 0;
		for (BandBean band : bandSet) {
			topBands.add(band);
			i++;
			if (i == NUMBER_OF_TOP_BAND_TO_SHOW_SONGS) {
				break;
			}
		}

		return topBands;
	}

}
