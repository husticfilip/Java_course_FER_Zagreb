package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().replaceFirst("/", "");
		// split to get nick/entryid
		String[] parts = path.split("/");

		if (parts.length == 1 && !parts[0].isEmpty()) {
			showBlogEntires(parts[0], req, resp);
		} else if (parts.length == 2) {
			showOneBlogEntry(parts[0], parts[1], req, resp);
		} else {
			sendErrorRequest("To many parameters provided in url.", req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo().replaceFirst("/", "");
		// split to get nick/entry_id
		String[] parts = path.split("/");

		if (parts.length == 2 && parts[1].equals("new")) {
			addNewBlogEntry(parts[0], req, resp);
		} else if (parts.length == 3 && parts[2].equals("edit")) {
			editBlogEntry(parts[0], parts[1], req, resp);
		} else {
			sendErrorRequest("You provided too many parameters in url.", req, resp);
		}

	}

	private void showBlogEntires(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUser blogUser = DAOProvider.getDAO().getBlogUsesrbyNickName(nick);
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntriesOfUser(blogUser);

		// so you can set attribute
		if (blogUser == null) {
			sendErrorRequest("User's nickname provided in url does not exist.", req, resp);
		} else {
			req.setAttribute("blogUserNick", blogUser.getNick());
			req.setAttribute("entires", entries);
			req.getRequestDispatcher("/servleti/author.jsp").forward(req, resp);
		}
	}

	private void showOneBlogEntry(String nick, String id, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUser blogUser = DAOProvider.getDAO().getBlogUsesrbyNickName(nick);
		Long blogId = null;
		try {
			blogId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			sendErrorRequest("Provided blog id is not an number.", req, resp);
			return;
		}

		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(blogId);

		if (blogUser == null) {
			sendErrorRequest("User's nickname provided in url does not exist.", req, resp);
		} else if (blogEntry == null) {
			sendErrorRequest("Blog entry with provided id does not exist.", req, resp);
		} else {
			List<BlogComment> entryComments = DAOProvider.getDAO().getBlogComments(blogEntry);

			req.setAttribute("entryComments", entryComments);
			req.setAttribute("blogEntry", blogEntry);
			req.getRequestDispatcher("/servleti/blogEntry.jsp").forward(req, resp);
		}

	}

	private void addNewBlogEntry(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUser blogUser = DAOProvider.getDAO().getBlogUsesrbyNickName(nick);

		if (blogUser == null) {
			sendErrorRequest("User's nickname provided in url does not exist.", req, resp);
		} else {
			BlogEntry newEntry = new BlogEntry();
			newEntry.setBlogUser(blogUser);
			newEntry.setComments(new ArrayList<BlogComment>());
			Date date = new Date();
			newEntry.setCreatedAt(date);
			newEntry.setLastModifiedAt(date);
			newEntry.setTitle(req.getParameter("title"));
			newEntry.setText(req.getParameter("text"));

			DAOProvider.getDAO().addBlogEntry(newEntry);
			blogUser.getBlogEntries().add(newEntry);
			resp.sendRedirect("/webapp-blog/servleti/author/" + blogUser.getNick());
		}
	}

	private void editBlogEntry(String nick, String id, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUser blogUser = DAOProvider.getDAO().getBlogUsesrbyNickName(nick);

		Long entryId = null;
		try {
			entryId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			sendErrorRequest("Provided blog id is not an number.", req, resp);
			return;
		}

		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);

		if (blogUser == null) {
			sendErrorRequest("User's nickname provided in url does not exist.", req, resp);
		} else if (blogEntry == null) {
			sendErrorRequest("Blog entry with provided id does not exist", req, resp);
		} else if (!blogEntry.getBlogUser().equals(blogUser)) {
			sendErrorRequest("You are not authorised to edit wanted blog entry.", req, resp);
		} else {
			blogEntry.setTitle(req.getParameter("title"));
			blogEntry.setText(req.getParameter("text"));
			DAOProvider.getDAO().updateBlogEntry(blogEntry);
			resp.sendRedirect("/webapp-blog/servleti/author/" + blogUser.getNick()+"/"+blogEntry.getId());
		}

	}

	private void sendErrorRequest(String error, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("authorError", error);
		req.getRequestDispatcher("/servleti/authorError.jsp").forward(req, resp);
	}

}
