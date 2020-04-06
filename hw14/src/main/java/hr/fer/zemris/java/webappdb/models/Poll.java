package hr.fer.zemris.java.webappdb.models;

/**
 * Class represents Poll entity in from database.
 * 
 * @author Filip Hustić
 *
 */
public class Poll {
	/**
	 * Id of poll.
	 */
	private long id;
	/**
	 * Title of poll.
	 */
	private String title;
	/**
	 * Message of poll.
	 */
	private String message;

	/**
	 * Basic constructor.
	 * 
	 * @param id      of poll.
	 * @param title   of poll.
	 * @param message of poll.
	 */
	public Poll(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

}
