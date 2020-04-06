package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents data base which holds studentRecords.
 * 
 * @author Filip Hustić
 *
 */
public class StudentDatabase {

	/**
	 * List of studentRecords.
	 */
	private List<StudentRecord> studentRecords;
	/**
	 * Map which maps jmbag with its index in studentRecords.
	 */
	private Map<String, Integer> indexMap;

	/**
	 * Constructor which takes in array of records in String format.
	 * 
	 * @param records array of student records in String format.
	 */
	public StudentDatabase(String[] records) {
		studentRecords = new ArrayList<StudentRecord>();
		indexMap = new HashMap<String, Integer>();

		for (String record : records) {
			record = record.trim().replaceAll("\\s+", " ");
			String[] recordElements = record.split(" ");

			if (recordElements.length < 4)
				throw new IllegalArgumentException("Record does not have all atributes.");

			testJmbag(recordElements[0]);
			String lastName = getLastName(recordElements);
			String firstNtame = recordElements[recordElements.length - 2];
			int grade = parseGrade(recordElements[recordElements.length - 1]);

			StudentRecord newRecord = new StudentRecord(recordElements[0], lastName, firstNtame, grade);

			studentRecords.add(newRecord);
			indexMap.put(newRecord.getJmbag(), studentRecords.size() - 1);
		}

	}

	/**
	 * Method returns student record with given jmbag.
	 * 
	 * @param jmbag of student record.
	 * @return record with given jmbag if record exists, null otherwise.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Integer index = indexMap.get(jmbag);

		if (index == null)
			return null;
		else
			return studentRecords.get(index);

	}

	/**
	 * Method takes in filter of studentRecords.
	 * <p>
	 * Method goes through all students records and filters them by given filter. If
	 * record goes through filtration it is added into new temporary list which is
	 * in the end returned.
	 * <p>
	 * 
	 * @param filter which filters student records.
	 * @return filtered list of student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {

		List<StudentRecord> temporaryList = new ArrayList<StudentRecord>();

		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record))
				temporaryList.add(record);
		}
		return temporaryList;
	}

	/**
	 * Helper method which gets last name from string record.
	 * 
	 * @param recordElements string record divided into string array.
	 * @return last name which is in studentRecord.
	 */
	private String getLastName(String[] recordElements) {

		StringBuilder builder = new StringBuilder();

		for (int i = 1, length = recordElements.length - 2; i < length; ++i) {
			builder.append(recordElements[i]);
		}

		return builder.toString();
	}

	/**
	 * Method checks if given jmbag is valid.
	 * 
	 * @param jmbag
	 * @throws IllegalArgumentException if given jmbag does not consists of 10
	 *                                  digits or if there is already record with
	 *                                  given jmbag in data base.
	 */
	private void testJmbag(String jmbag) {
		if (jmbag.length() != 10)
			throw new IllegalArgumentException("Invalid jmbag.");

		if (indexMap.containsKey(jmbag))
			throw new IllegalArgumentException("Given jmbag already exists.");
	}

	/**
	 * Method parses given value into int and checks if it is valid grade.
	 * 
	 * @param value to be parsed.
	 * @return parsed grade.
	 * @throws IllegalArgumentException if value can not be parsed into int or if
	 *                                  grade is less than 1 or greater than 5.
	 */
	private int parseGrade(String value) {
		int grade;
		try {
			grade = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Grade was not a number.");
		}

		if (grade < 1 || grade > 5)
			throw new IllegalArgumentException("Given grade was out of range.");

		return grade;
	}

}
