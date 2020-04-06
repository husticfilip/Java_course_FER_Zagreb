package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(Long id) {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}

	@Override
	public BlogUser getBlogUsesrbyNickName(String nickName) {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("select us from BlogUser as us Where nick=:ni").setParameter("ni", nickName)
				.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}


	@Override
	public List<BlogUser> getAllBlogUsers() {
		return JPAEMProvider.getEntityManager().createQuery("Select a from BlogUser a", BlogUser.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntriesOfUser(BlogUser blogUser) {
		return JPAEMProvider.getEntityManager().createQuery("Select be from BlogEntry as be where be.blogUser=:bu").setParameter("bu", blogUser).getResultList();
	}

	@Override
	public void addBlogUser(BlogUser blogUser) {
		JPAEMProvider.getEntityManager().persist(blogUser);
	}

	@Override
	public void addBlogEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().persist(blogEntry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) {
		return JPAEMProvider.getEntityManager().createQuery("Select bc from BlogComment as bc where bc.blogEntry=:be").setParameter("be", blogEntry).getResultList();
	}

	@Override
	public void addBlogComment(BlogComment blogComment) {
		JPAEMProvider.getEntityManager().persist(blogComment);
	}

	@Override
	public BlogEntry updateBlogEntry(BlogEntry blogEntry) {
		return JPAEMProvider.getEntityManager().merge(blogEntry);
	}

}