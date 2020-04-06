package hr.fer.zemris.java.hw05.db;

/**
 * Method which represents filter of studentRecord.
 * 
 * @author Filip Hustić
 *
 */
public interface IFilter {

	/**
	 *
	 * @param record studentRecord
	 * @return If record satisfies some conditions true is returned, false
	 *         otherwise.
	 */
	public boolean accepts(StudentRecord record);

}
