package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Method preprocesses table of trigonometric values. Values are sin(x) and
 * cos(x).
 * 
 * @author Filip Hustić
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = getIntValue(req.getParameter("a"), 0);
		int b = getIntValue(req.getParameter("b"), 360);

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		ArrayList<TrigBean> values = new ArrayList<TrigBean>();

		for (int x = a; x <= b; ++x) {
			values.add(new TrigBean(x, Math.sin(x), Math.cos(x)));
		}

		req.setAttribute("sinCosList", values);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

	}

	/**
	 * Method returns integer value of val if val can be parsed into int. If it can
	 * not provided default values is returned.
	 * 
	 * @param val        value to be paresd.
	 * @param defaultVal default value.
	 * @return int value of @param val or default value if @param val can not be
	 *         parsed.
	 */
	private int getIntValue(String val, int defaultVal) {
		if (val == null) {
			return defaultVal;
		}

		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException ex) {
			return defaultVal;
		}
	}

	/**
	 * Bean which holds sin and cos value of given x.
	 * 
	 * @author Filip Hustić
	 *
	 */
	public static class TrigBean {
		/**
		 * Value x.
		 */
		private int x;
		/**
		 * Sin of x value.
		 */
		private double sinX;
		/**
		 * Cos of x value.
		 */
		private double cosX;

		public TrigBean(int x, double sinX, double cosX) {
			super();
			this.x = x;
			this.sinX = sinX;
			this.cosX = cosX;
		}

		public int getX() {
			return x;
		}

		public double getSinX() {
			return sinX;
		}

		public double getCosX() {
			return cosX;
		}

	}

}
