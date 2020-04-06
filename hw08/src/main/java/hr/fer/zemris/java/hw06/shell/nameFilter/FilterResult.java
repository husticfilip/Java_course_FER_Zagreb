package hr.fer.zemris.java.hw06.shell.nameFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Class which represents one file and information about found groups in name of
 * the file.
 * 
 * @author Filip Hustić
 *
 */
public class FilterResult {
	private List<String> groups;
	private String fileName;
	private boolean satisfiable = false;

	public FilterResult(String fileName, String pattern) {
		this.fileName = new File(fileName).getName();
		this.groups = new ArrayList<String>();
		match(pattern);
	}

	@Override
	public String toString() {
		return fileName;
	}

	/**
	 * 
	 * @return number of groups. 0 group is not included.
	 */
	public int numberOfGroups() {
		return groups.size() - 1;
	}

	/**
	 * Method returns group at given index.
	 * 
	 * @param index of the group.
	 * @return group at given index.
	 * @throws IndexOutOfBoundsException if given index is not index of the group.
	 */
	public String group(int index) {
		return groups.get(index);
	}

	/**
	 * Method which checks if fileName matches given pattern. If it does groups of
	 * provided in pattern are made and added to groups list.
	 * 
	 * @param pattern regex.
	 * @throws PatternSyntaxException if provided pattern is not valid.
	 */
	private void match(String regex) {
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(fileName);

		// pattern was not found.
		if ((satisfiable = matcher.matches()) == false) {
			return;
		}

		for (int i = 0, numOfGrups = matcher.groupCount(); i <= numOfGrups; ++i) {
			groups.add(matcher.group(i));
		}
	}

	/**
	 * 
	 * @return true if fileName matches pattern, false otherwise.
	 */
	public boolean isSatisfiable() {
		return satisfiable;
	}

	/**
	 * 
	 * @param dir
	 * @param pattern
	 * @return
	 * @throws IOException
	 * @throws             PatternSyntaxException.
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException, PatternSyntaxException {
		return Files.walk(dir).filter(Files::isRegularFile).map(f -> new FilterResult(f.toString(), pattern))
				.filter(fr -> fr.isSatisfiable()).collect(Collectors.toList());
	}

}
