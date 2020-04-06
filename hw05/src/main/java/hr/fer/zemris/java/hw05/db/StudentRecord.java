package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents one student record.
 * 
 * @author Filip Hustić
 *
 */
public class StudentRecord {

	/**
	 * Jmbag of the student.
	 */
	private String jmbag;
	/**
	 * Last name of the student.
	 */
	private String lastName;
	/**
	 * First name of the student.
	 */
	private String firstName;
	/**
	 * Final grade of the student.
	 */
	private int finalGrade;

	/**
	 * Constructor which takes in jmbag, lastName, firstName and finalGrade of
	 * student.
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

}
