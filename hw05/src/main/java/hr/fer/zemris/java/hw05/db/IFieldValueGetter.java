package hr.fer.zemris.java.hw05.db;

/**
 * Interface which represents getter of field in student record.
 * @author Filip Hustić
 *
 */
public interface IFieldValueGetter {

	/**
	 * 
	 * @param record studentRecord from which attribute will be returned.
	 * @return attribute of studentRecord
	 */
	public String get(StudentRecord record);
	
}
