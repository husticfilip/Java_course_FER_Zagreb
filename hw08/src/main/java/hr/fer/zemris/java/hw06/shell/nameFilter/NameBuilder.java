package hr.fer.zemris.java.hw06.shell.nameFilter;

/**
 * Interface which represents name builder.
 * 
 * @author Filip Hustić
 *
 */
public interface NameBuilder {

	/**
	 * Method appends part of name to string builder. Part of the name can be
	 * regular string or one of the groups in FilterResult.
	 * 
	 * @param result result of filtering fileName with pattern.
	 * @param sb     stringBuilder.
	 */
	void execute(FilterResult result, StringBuilder sb);

}
