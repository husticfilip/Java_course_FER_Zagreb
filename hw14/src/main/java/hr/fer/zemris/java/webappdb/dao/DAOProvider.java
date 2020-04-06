package hr.fer.zemris.java.webappdb.dao;

import hr.fer.zemris.java.webappdb.dao.sql.SQLDAO;

/**
 * Class which provides singleton of SQLDAO.
 * 
 * @author Filip Hustić
 *
 */
public class DAOProvider {

	/**
	 * SQLDAO singleton.
	 */
	private static DAO dao = new SQLDAO();

	private DAOProvider() {
	}

	/**
	 * Method returns DAO object.
	 * 
	 * @return
	 */
	public static DAO getDao() {
		return dao;
	}

}
