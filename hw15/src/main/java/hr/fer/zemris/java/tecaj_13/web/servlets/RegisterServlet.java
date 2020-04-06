package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final int SESSION_TIME = 5*60;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser user = new BlogUser();

		user.setFirstName(req.getParameter("firstName"));
		user.setLastName(req.getParameter("lastName"));

		List<String> invalidReasons = null;

		String password = req.getParameter("password");
		if (!password.equals(req.getParameter("repeatedPassword"))) {
			invalidReasons = new ArrayList<String>();
			invalidReasons.add("Password does not match repeated password.");
		} else {
			user.setPasswordHash(ServletsUtil.digestPassword(password));
		}

		String nick = req.getParameter("nickName");
		if (nickExist(nick)) {
			invalidReasons = invalidReasons == null ? new ArrayList<String>() : invalidReasons;
			invalidReasons.add("Nickname already exist.");
		} else {
			user.setNick(nick);
		}

		String email = req.getParameter("email");
		if (!validEmail(email)) {
			invalidReasons = invalidReasons == null ? new ArrayList<String>() : invalidReasons;
			invalidReasons.add("Email is not valid.");
		} else {
			user.setEmail(email);
		}
		
		//there were some mistakes in fill up form
		if (invalidReasons != null) {
			req.setAttribute("invalidReasons", invalidReasons);
			req.getRequestDispatcher("registerError.jsp").forward(req, resp);
		} else {
			//set registered user and set his session
			DAOProvider.getDAO().addBlogUser(user);

			HttpSession oldSession = req.getSession(false);

			if (oldSession != null) {
				oldSession.invalidate();
			}

			HttpSession newSession = req.getSession(true);
			newSession.setMaxInactiveInterval(SESSION_TIME);

			newSession.setAttribute("ActiveUserNick", user.getNick());
			newSession.setAttribute("ActiveUserID", user.getId());
			req.getRequestDispatcher("registerSuccess.jsp").forward(req, resp);

		}
	}

	private boolean nickExist(String nick) {
		BlogUser userWithNick = DAOProvider.getDAO().getBlogUsesrbyNickName(nick);
		
		return userWithNick != null;	
				
	}

	private boolean validEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

}
