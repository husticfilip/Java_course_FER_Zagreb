package hr.fer.zemris.java.webapp.beans;

/**
 * Bean which holds information about band in band voting.
 * 
 * @author Filip Hustić
 *
 */
public class BandBean {
	/**
	 * Band id.
	 */
	private String id;
	/**
	 * Band name.
	 */
	private String name;
	/**
	 * Link to the song of band.
	 */
	private String songLink;
	/**
	 * Number of votes band received.
	 */
	private int numOfVotes;

	public BandBean(String id, String name, String songLink, int numOfVotes) {
		super();
		this.id = id;
		this.name = name;
		this.songLink = songLink;
		this.numOfVotes = numOfVotes;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSongLink() {
		return songLink;
	}

	public int getNumOfVotes() {
		return numOfVotes;
	}

	public void setNumOfVotes(int numOfVotes) {
		this.numOfVotes = numOfVotes;
	}

}
