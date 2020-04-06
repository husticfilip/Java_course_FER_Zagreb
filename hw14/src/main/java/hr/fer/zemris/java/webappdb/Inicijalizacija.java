package hr.fer.zemris.java.webappdb;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {
	/**
	 * Relative path to the database properties file.
	 */
	private static final String PROPERTIES_PATH = "/WEB-INF/dbsettings.properties";

	/**
	 * Name of Polls table.
	 */
	private static final String POLLS_TABLE_NAME = "Polls";
	/**
	 * Name of PollOptions table.
	 */
	private static final String POLL_OPTIONS_TABLE_NAME = "PollOptions";

	/**
	 * Query for creating Polls table.
	 */
	private static final String POOLS_TABLE_CREATE_QUERY = "CREATE TABLE Polls\r\n"
			+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + " title VARCHAR(150) NOT NULL,\r\n"
			+ " message CLOB(2048) NOT NULL\r\n" + ")";

	/**
	 * Query for creating PollOptions table.
	 */
	private static final String POOL_OPTIONS_TABLE_CREATE_QUERY = "CREATE TABLE PollOptions\r\n"
			+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + " optionTitle VARCHAR(100) NOT NULL,\r\n"
			+ " optionLink VARCHAR(150) NOT NULL,\r\n" + " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
			+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties props = new Properties();

		try {
			props.load(new FileInputStream(new File(sce.getServletContext().getRealPath(PROPERTIES_PATH))));

			String host = props.getProperty("host");
			int port = Integer.parseInt(props.getProperty("port"));
			String dbName = props.getProperty("name");
			String user = props.getProperty("user");
			String password = props.getProperty("password");

			if (host == null || dbName == null || user == null || password == null) {
				throw new RuntimeException(
						"Some property for database initialization was not defined. Needed properties are host, port, name(of database), user and password");
			}

			String connectionURL = "jdbc:derby://" + host + ":" + String.valueOf(port) + "/" + dbName + ";user=" + user
					+ ";password=" + password;
			ComboPooledDataSource cpds = new ComboPooledDataSource();

			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
			}

			cpds.setJdbcUrl(connectionURL);
			long bandsPoolId = initilizePollTable(cpds);
			initilizePollOptionsTable(cpds, bandsPoolId);

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Database property file does not exist");
		} catch (NumberFormatException e) {
			throw new RuntimeException("Database port was not defined or was not number.");
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method creates Polls table if one is not created and populates it with
	 * favorite band poll.
	 * 
	 * @param cpds database source.
	 * @return id of favorite band poll in Polls table.
	 */
	private long initilizePollTable(ComboPooledDataSource cpds) {

		try {
			Connection con = cpds.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, POLLS_TABLE_NAME.toUpperCase(), null);

			// table exists
			if (rs.next()) {

				ResultSet result = con.createStatement()
						.executeQuery("SELECT id FROM " + POLLS_TABLE_NAME + " WHERE title='Band pool'");

				if (!result.next()) {
					return insertIntoPollTable("Band pool", "Woting for favorite band.", con);
				} else {
					return result.getLong(1);
				}

			} // does not exist
			else {
				createTable(POOLS_TABLE_CREATE_QUERY, con);
				return insertIntoPollTable("Band pool", "Woting for favorite band.", con);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Exception while configuring databses", e);
		}
	}

	/**
	 * MEthod creates PollOptions table if one does not exist and populates it with
	 * bands for favorite band Poll.
	 * 
	 * @param cpds   database source.
	 * @param pollId id of favorite band poll.
	 */
	private void initilizePollOptionsTable(ComboPooledDataSource cpds, long pollId) {

		try {
			Connection con = cpds.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, POLL_OPTIONS_TABLE_NAME.toUpperCase(), null);

			// table exists
			if (rs.next()) {

				ResultSet result = con.createStatement()
						.executeQuery("SELECT COUNT(*) FROM " + POLL_OPTIONS_TABLE_NAME);

				result.next();
				if (result.getString(1).equals("0")) {
					initialPollOptionsEntities(con, pollId);
				}

			} // does not exist
			else {
				createTable(POOL_OPTIONS_TABLE_CREATE_QUERY, con);
				initialPollOptionsEntities(con, pollId);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Exception while configuring databses", e);
		}
	}

	/**
	 * Method populates PollOptions table with bands entities which will be used in
	 * favorite band poll.
	 * 
	 * @param con    connection to database.
	 * @param pollId id of favorite band poll.
	 * @throws SQLException
	 */
	private void initialPollOptionsEntities(Connection con, Long pollId) throws SQLException {
		insertIntoPollOptionsTable("The Beatles", "https://www.youtube.com/watch?v=FhXU8c8qwXs", pollId, 0, con);
		insertIntoPollOptionsTable("Led Zeppelin", "https://www.youtube.com/watch?v=sqUkPzFmfxo", pollId, 0, con);
		insertIntoPollOptionsTable("Black Sabbath", "https://www.youtube.com/watch?v=8aQRq9hhekA", pollId, 0, con);
		insertIntoPollOptionsTable("Queen", "https://www.youtube.com/watch?v=", pollId, 0, con);
		insertIntoPollOptionsTable("ACDC", "https://www.youtube.com/watch?v=XRPwNU3f3Tw", pollId, 0, con);
		insertIntoPollOptionsTable("The Who", "https://www.youtube.com/watch?v=x2KRpRMSu4g", pollId, 0, con);
		insertIntoPollOptionsTable("The Rolling Stones", "https://www.youtube.com/watch?v=O4irXQhgMqg", pollId, 0, con);
		insertIntoPollOptionsTable("Ado Gegaj", "https://www.youtube.com/watch?v=9hL8gQU5yus", pollId, 1, con);
	}

	/**
	 * Method creates table in database provided with connection.
	 * 
	 * @param query create table query.
	 * @param con   connection to database.
	 */
	private void createTable(String query, Connection con) {

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Wrong database query.", e);
		}
	}

	/**
	 * Method inserts entity(Poll) into Polls table.
	 * 
	 * @param title   of Poll.
	 * @param message of Poll.
	 * @param con     connection to database.
	 * @return id of newly created poll.
	 * @throws SQLException if there was exception while executing query.
	 */
	private long insertIntoPollTable(String title, String message, Connection con) throws SQLException {
		PreparedStatement statement = con.prepareStatement(
				"INSERT INTO " + POLLS_TABLE_NAME + " (title,message) values(?,?)", Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, title);
		statement.setString(2, message);
		statement.execute();
		ResultSet rset = statement.getGeneratedKeys();
		rset.next();
		return rset.getLong(1);
	}

	/**
	 * MEthod inserts entity(PollOption) into PollOptions table.
	 * 
	 * @param optionTitle title of option.
	 * @param optionLink  link of option resource.
	 * @param poolID      id of poll in which this poll participates.
	 * @param votesCount  initial poll count of option.
	 * @param con         connection to database.
	 * @throws SQLException if there was exception while executing query.
	 */
	private void insertIntoPollOptionsTable(String optionTitle, String optionLink, long poolID, int votesCount,
			Connection con) throws SQLException {
		PreparedStatement statement = con.prepareStatement("INSERT INTO " + POLL_OPTIONS_TABLE_NAME
				+ " (optionTitle,optionLink,pollID,votesCount) values(?,?,?,?)");
		statement.setString(1, optionTitle);
		statement.setString(2, optionLink);
		statement.setLong(3, poolID);
		statement.setInt(4, votesCount);
		statement.execute();
	}

}