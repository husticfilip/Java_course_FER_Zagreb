package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Class holds informations about student and his success in one subject.
 * 
 * @author Filip Hustić
 *
 */
public class StudentRecord {
	/**
	 * Jmbag of student.
	 */
	private String jmbag;
	/**
	 * Surname of student.
	 */
	private String prezime;
	/**
	 * Name of student.
	 */
	private String ime;
	/**
	 * Points of mid term exam.
	 */
	private double bodoviMi;
	/**
	 * Points of end term exam.
	 */
	private double bodoviZi;
	/**
	 * Points of lab.
	 */
	private double bodoviLab;
	/**
	 * Grade.
	 */
	private int ocijena;

	/**
	 * Constructor which takes in informations about student and subject.
	 * <p>
	 * Jmbag consists 10 digits.
	 * <p>
	 * <p>
	 * Ocijena is grade between 1 and 5 including.
	 * <p>
	 * 
	 * @param jmbag     jmbag of student.
	 * @param prezime   surname of student.
	 * @param ime       name of student.
	 * @param bodoviMi  points of mid term exam.
	 * @param bodoviZi  points of end term exam.
	 * @param bodoviLab points of lab.
	 * @param ocijena   grade.
	 * @throws IllegalArgumentException if grade is less than 1 or grater than 5. Or
	 *                                  if jmbag is not in right form.
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double bodoviMi, double bodoviZi, double bodoviLab,
			int ocijena) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(prezime);
		Objects.requireNonNull(ime);

		if (jmbag.matches("/^[0-9]+$/") || jmbag.length() != 10) {
			throw new IllegalArgumentException("Illegal jmbag.");
		}
		if (ocijena < 1 || ocijena > 5) {
			throw new IllegalArgumentException("Illegal ocijena");
		}

		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviMi = bodoviMi;
		this.bodoviZi = bodoviZi;
		this.bodoviLab = bodoviLab;
		this.ocijena = ocijena;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %f %f %f %d", jmbag, prezime, ime, bodoviMi, bodoviZi, bodoviLab, ocijena);
	}

	public String getJmbag() {
		return jmbag;
	}

	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public double getBodoviMi() {
		return bodoviMi;
	}

	public void setBodoviMi(double bodoviMi) {
		this.bodoviMi = bodoviMi;
	}

	public double getBodoviZi() {
		return bodoviZi;
	}

	public void setBodoviZi(double bodoviZi) {
		this.bodoviZi = bodoviZi;
	}

	public double getBodoviLab() {
		return bodoviLab;
	}

	public void setBodoviLab(double bodoviLab) {
		this.bodoviLab = bodoviLab;
	}

	public int getOcijena() {
		return ocijena;
	}

	public void setOcijena(int ocijena) {
		this.ocijena = ocijena;
	}

}
