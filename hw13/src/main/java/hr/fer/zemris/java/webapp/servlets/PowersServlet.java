package hr.fer.zemris.java.webapp.servlets;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servler used to generate excel file containing powers of numbers within
 * specified range.
 * 
 * @author Filip Hustić
 *
 */
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lower bound of number to be powed.
	 */
	private static final int LOWER_NUMBER_BOUND = -100;
	/**
	 * Upper bound of number to be powed.
	 */
	private static final int UPPER_NUMBER_BOUND = 100;

	/**
	 * Lower bound of potention.
	 */
	private static final int LOWER_POWER_BOUND = 1;
	/**
	 * Upper bound of potention.
	 */
	private static final int UPPER_POWER_BOUND = 5;

	/**
	 * Relative path to excel in which result will be written.
	 */
	private static final String EXCEL_DEST = "/files/excels/powersExcel.xls";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("lowerNumBound", LOWER_NUMBER_BOUND);
		req.setAttribute("upperNumBound", UPPER_NUMBER_BOUND);
		req.setAttribute("lowerPowBound", LOWER_POWER_BOUND);
		req.setAttribute("upperPowBound", UPPER_POWER_BOUND);

		int a = 0;
		int b = 0;
		int n = 0;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException ex) {
			req.getRequestDispatcher("WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		if (a < LOWER_NUMBER_BOUND || b > UPPER_NUMBER_BOUND) {
			req.getRequestDispatcher("WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		if (n < LOWER_POWER_BOUND || n > UPPER_NUMBER_BOUND) {
			req.getRequestDispatcher("WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		try {
			writeExcel(a, b, n);
		} catch (IOException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Internal server error. Error while generating Excel file.");
		}

		req.getRequestDispatcher("WEB-INF/pages/powersSuccess.jsp").forward(req, resp);
	}

	/**
	 * Method which writes potentions of numbers between given boundaries into excel
	 * file.
	 * 
	 * @param a lower boundary.
	 * @param b upper boundary.
	 * @param n range of potentions.
	 * @throws IOException if there were error while writing to excel.
	 */
	public void writeExcel(int a, int b, int n) throws IOException {
		@SuppressWarnings("resource")
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; ++i) {
			int rowNumber = 0;

			HSSFSheet sheet = hwb.createSheet("On power " + i);
			HSSFRow rowHead = sheet.createRow(rowNumber++);
			rowHead.createCell(0).setCellValue("NUMBER");
			rowHead.createCell(1).setCellValue("POWER " + i);

			for (int j = a; j <= b; ++j) {
				HSSFRow row = sheet.createRow(rowNumber++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}

		}

		FileOutputStream fileOut = new FileOutputStream(getServletContext().getRealPath(".") + EXCEL_DEST);
		hwb.write(fileOut);
		fileOut.close();

	}

}
