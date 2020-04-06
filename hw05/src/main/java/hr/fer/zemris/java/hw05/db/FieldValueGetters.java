package hr.fer.zemris.java.hw05.db;

/**
 * Class that holds fieldGetters which implement IFieldValueGetter
 * @author Filip Hustić
 *
 */
public class FieldValueGetters {
	
	/**
	 * Getter that gets first name of given record.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record)->record.getFirstName();
	/**
	 * Getter that gets last name of given record.
	 */
	public static final IFieldValueGetter LAST_NAME = (record)->record.getLastName();
	/**
	 * Getter that gets jmbag of given record.
	 */
	public static final IFieldValueGetter JMBAG = (record)->record.getJmbag();


}
