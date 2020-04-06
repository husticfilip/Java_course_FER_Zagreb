package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface representing data access layer for blog webapp.
 * 
 * @author Filip Hustić
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id);

	/**
	 * Gets BlogUser with provided id from database.
	 * 
	 * @param id of BlogUser.
	 * @return BlogUser with given id or null if user does not exist.
	 */
	public BlogUser getBlogUser(Long id);

	/**
	 * Gets BlogUser with provided nickname from database.
	 * 
	 * @param nickName nickname of user.
	 * @return BlogUser with given nickName or null if user does not exist.
	 */
	public BlogUser getBlogUsesrbyNickName(String nickName);

	/**
	 * 
	 * @return all BlogUsers from databse.
	 */
	public List<BlogUser> getAllBlogUsers();

	/**
	 * Returns BlogEntries of provided BlogUser from database.
	 * 
	 * @param blogUser
	 * @return List of BlogEntires of provided BlogUser or empty list if there are
	 *         no entries.
	 */
	public List<BlogEntry> getBlogEntriesOfUser(BlogUser blogUser);

	/**
	 * Method adds blogUser to database.
	 * 
	 * @param blogUser to be added to database.
	 */
	public void addBlogUser(BlogUser blogUser);

	void addBlogEntry(BlogEntry blogEntry);

	/**
	 * Returns BlogComments of provided BlogEntry from database.
	 * 
	 * @param blogEntry
	 * @return List of BlogComments of provided BlogEntry or empty list if there are
	 *         no comments.
	 */
	List<BlogComment> getBlogComments(BlogEntry blogEntry);

	/**
	 * Method adds blogComment to database.
	 * 
	 * @param blogComment to be added to database.
	 */
	void addBlogComment(BlogComment blogComment);

	/**
	 * Method updates provided blogEntry in database.
	 * 
	 * @param blogEntry
	 * @return updated version of blogEntry.
	 */
	public BlogEntry updateBlogEntry(BlogEntry blogEntry);

}
