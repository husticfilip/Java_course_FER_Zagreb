package hr.fer.zemris.java.webappdb.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hr.fer.zemris.java.webappdb.dao.DAO;
import hr.fer.zemris.java.webappdb.models.Poll;
import hr.fer.zemris.java.webappdb.models.PollOption;

/**
 * DAO object.
 * 
 * @author Filip Hustić
 *
 */
public class SQLDAO implements DAO {

	@Override
	public ArrayList<Poll> getPools() {
		Connection con = SQLConnectionProvider.getConnection();
		ArrayList<Poll> polls = new ArrayList<>();

		ResultSet rset = null;
		try {
			rset = con.prepareStatement("SELECT * FROM Polls").executeQuery();

			while (rset.next()) {
				Poll poll = new Poll(rset.getLong(1), rset.getString(2), rset.getString(3));
				polls.add(poll);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error while getting pools from database.", e);
		}

		return polls;
	}

	@Override
	public ArrayList<PollOption> getPoolOptions(long poolID) {
		Connection con = SQLConnectionProvider.getConnection();
		ArrayList<PollOption> options = new ArrayList<PollOption>();

		ResultSet rset;
		try {
			rset = con.prepareStatement("SELECT * FROM PollOptions WHERE pollID=" + poolID).executeQuery();

			while (rset.next()) {
				PollOption option = new PollOption(rset.getLong(1), rset.getString(2), rset.getString(3),
						rset.getLong(4), rset.getInt(5));
				options.add(option);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error while getting pool options from database", e);
		}

		return options;
	}

	@Override
	public void registerVote(long pollId, long optionId) {
		Connection con = SQLConnectionProvider.getConnection();
		try {
			con.prepareStatement(
					"UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id=" + optionId + " AND pollId=" + pollId)
					.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Error while updating pool options from database", e);
		}
	}

}
