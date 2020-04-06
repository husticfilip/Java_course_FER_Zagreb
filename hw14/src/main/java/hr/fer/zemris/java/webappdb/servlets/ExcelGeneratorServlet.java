package hr.fer.zemris.java.webappdb.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.webappdb.dao.DAOProvider;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * Servlet which generates Excel file which contains information about poll
 * which id was provided.
 * 
 * @author Filip Hustić
 *
 */
@WebServlet("/servleti/gnerateExcel")
public class ExcelGeneratorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollId = Long.parseLong(req.getParameter("pollId"));
		ArrayList<PollOption> options = DAOProvider.getDao().getPoolOptions(pollId);

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Results");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("Option");
		row.createCell(1).setCellValue("Result");

		int rowNum = 1;
		for (PollOption option : options) {
			row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
			rowNum++;
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=Results.xls");
		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
