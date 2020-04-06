package hr.fer.zemris.java.webappdb.models;

/**
 * Class represents one PollOption entity from database.
 * 
 * @author Filip Hustić
 *
 */
public class PollOption {

	/**
	 * Id of pollOption.
	 */
	private long id;
	/**
	 * Title of pollOption.
	 */
	private String optionTitle;
	/**
	 * Link to the pollOption resource.
	 */
	private String optionLink;
	/**
	 * Id of poll in which this pollOption participates.
	 */
	private long poolId;
	/**
	 * Number of votes this pollOptions has gotten.
	 */
	private int votesCount;

	/**
	 * Basic constructor.
	 * 
	 * @param id          of pollOption.
	 * @param optionTitle of pollOption.
	 * @param optionLink  link to the pollOption resource.
	 * @param poolId      id of poll in which this pollOption participates.
	 * @param votesCount  number of votes this pollOptions has gotten.
	 */
	public PollOption(long id, String optionTitle, String optionLink, long poolId, int votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.poolId = poolId;
		this.votesCount = votesCount;
	}

	public long getId() {
		return id;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public long getPoolId() {
		return poolId;
	}

	public int getVotesCount() {
		return votesCount;
	}

}
