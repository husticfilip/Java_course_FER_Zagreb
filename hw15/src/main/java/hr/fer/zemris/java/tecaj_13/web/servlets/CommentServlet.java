package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
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

@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String activeUserNick = (String) req.getSession().getAttribute("ActiveUserNick");
		String comment = (String) req.getParameter("comment");
		Long entryId = null;
		try {
			entryId = Long.parseLong(req.getParameter("entryId"));
		} catch (Exception e) {
			req.setAttribute("authorError", "Provided blog entry id is not an number.");
			req.getRequestDispatcher("/servleti/authorError.jsp").forward(req, resp);
			return;
		}

		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);

		if (blogEntry == null) {
			req.setAttribute("authorError", "Blog entry with provided id does not exist.");
			req.getRequestDispatcher("/servleti/authorError.jsp").forward(req, resp);

		} else {
			BlogComment blogComment = new BlogComment();
			blogComment.setBlogEntry(blogEntry);
			blogComment.setMessage(comment);
			blogComment.setPostedOn(new Date());
			blogComment.setUsersNick(activeUserNick);
			
			DAOProvider.getDAO().addBlogComment(blogComment);
			List<BlogComment> entryComments = DAOProvider.getDAO().getBlogComments(blogEntry);
			req.setAttribute("entryComments", entryComments);
			req.setAttribute("blogEntry", blogEntry);
			req.getRequestDispatcher("/servleti/blogEntry.jsp").forward(req, resp);
		}
	}
}
