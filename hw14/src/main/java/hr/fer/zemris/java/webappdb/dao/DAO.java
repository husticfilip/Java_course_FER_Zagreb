package hr.fer.zemris.java.webappdb.dao;

import java.util.ArrayList;

import hr.fer.zemris.java.webappdb.models.Poll;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * Interface representing data access layer.
 * 
 * @author Filip Hustić
 *
 */
public interface DAO {

	/**
	 * Method returns polls from Polls table in database.
	 * 
	 * @return polls.
	 */
	public ArrayList<Poll> getPools();

	/**
	 * Method returns poll options from PollOptions table in database. Poll options
	 * returned are ones from Poll which id is provided.
	 * 
	 * @param pollId id of the poll in which returned poll options participate.
	 * @return poll options.
	 */
	public ArrayList<PollOption> getPoolOptions(long pollId);

	/**
	 * Method reqisters vote in poll.
	 * 
	 * @param pollId   id of poll.
	 * @param optionId id of option for which vote is given.
	 */
	public void registerVote(long pollId, long optionId);

}