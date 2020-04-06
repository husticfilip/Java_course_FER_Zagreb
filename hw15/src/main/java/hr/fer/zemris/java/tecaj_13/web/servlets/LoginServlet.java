package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int SESSION_TIME = 5*60;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser user = DAOProvider.getDAO().getBlogUsesrbyNickName(req.getParameter("nick"));

		if (user == null) {
			req.getSession().setAttribute("LoginError", "Given nickname does not exist.");

		} else if (!user.getPasswordHash().equals(ServletsUtil.digestPassword(req.getParameter("password")))) {
			req.getSession().setAttribute("LoginError", "Wrong password.");

		} else {
			HttpSession oldSession = req.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}

			HttpSession newSession = req.getSession(true);
			newSession.setMaxInactiveInterval(SESSION_TIME);

			System.out.println("postavljam ActiveUserNick");
			newSession.setAttribute("ActiveUserNick", user.getNick());
			newSession.setAttribute("ActiveUserID", user.getId());

		}
		
		resp.sendRedirect(req.getContextPath());
	}
}
