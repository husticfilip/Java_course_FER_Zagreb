package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/edit/*")
public class EditEntryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().replaceFirst("/", "");
		// split to get nick/entryid
		String[] parts = path.split("/");
	
		if (parts.length != 2) {
			sendErrorRequest("Not right number of parameters provided in url. Needed two parameters.", req, resp);
			return;
		}

		String nickName = parts[0];
		Long entryId = null;
		try {
			entryId = Long.parseLong(parts[1]);
		} catch (NumberFormatException e) {
			sendErrorRequest("Provided entrys id is not an number.", req, resp);
			return;
		}

		BlogUser blogUser = DAOProvider.getDAO().getBlogUsesrbyNickName(nickName);
		if (blogUser == null) {
			sendErrorRequest("User's nickname provided in url does not exist.", req, resp);
			return;
		}

		String activeUserNick = (String) req.getSession().getAttribute("ActiveUserNick");
		if (activeUserNick==null || !blogUser.getNick().equals(activeUserNick)) {
			sendErrorRequest("You are not authorized to edit this entry.", req, resp);
			return;
		}

		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
		if (blogEntry == null) {
			sendErrorRequest("Blog entry with provided id does not exist.", req, resp);
		} else if (!blogEntry.getBlogUser().equals(blogUser)) {
			sendErrorRequest("Blog entry does not belong to given user.", req, resp);
		} else {
			req.setAttribute("blogEntry", blogEntry);
			req.getRequestDispatcher("/servleti/editEntry.jsp").forward(req, resp);;
		}
	}

	private void sendErrorRequest(String error, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("authorError", error);
		req.getRequestDispatcher("/servleti/authorError.jsp").forward(req, resp);
	}

}
