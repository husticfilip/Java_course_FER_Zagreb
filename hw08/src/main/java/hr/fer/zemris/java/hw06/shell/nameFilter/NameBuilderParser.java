package hr.fer.zemris.java.hw06.shell.nameFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents parser of new file names. It uses expression which denotes
 * how new name should look like.
 * 
 * @author Filip Hustić
 *
 */
public class NameBuilderParser {

	/**
	 * List of builders which will build new name.
	 */
	private List<NameBuilder> buildersList;

	/**
	 * Default constructor which initializes NameBuilders which will compose new
	 * name.
	 * <p>
	 * Expression can be consisted of reglar strings and
	 * substitutions(${numOfGrop}). Substitution places number of group from
	 * filtered file name in its place.
	 * <p>
	 * 
	 * @param expression by which blueprint new name will be composed.
	 * @throws NameParserException if expression is not in valid form.
	 */
	public NameBuilderParser(String expression) {
		buildersList = new ArrayList<NameBuilder>();

		while (true) {
			int indexOfSubs = expression.indexOf("${");

			if (indexOfSubs == -1) {
				buildersList.add(text(expression));
				break;
			} else {
				// all until ${
				buildersList.add(text(expression.substring(0, indexOfSubs)));
				// cut added part + ${
				expression = expression.substring(indexOfSubs + 2);
				// add substitution
				buildersList.add(getSubstitutionBuilder(expression));
				// cut off }
				expression = expression.substring(expression.indexOf("}") + 1);
			}
		}

	}

	/**
	 * Method returns nameBuilder which holds all other builders and in its execute
	 * method calls them to form new file name.
	 * 
	 * @return nameBuilder.
	 */
	public NameBuilder getNameBuilder() {
		return (filterResult, sb) -> buildersList.stream().forEach(builder -> builder.execute(filterResult, sb));
	}

	/**
	 * Method builds up next substitution in expression.
	 * 
	 * @param expression which contains substitution.
	 * @return substitution builder.
	 * @throws NameBuilderParser if expression is not valid.
	 */
	private NameBuilder getSubstitutionBuilder(String expression) {
		int indexOfEndSub = expression.indexOf("}");
		if (indexOfEndSub == -1) {
			throw new NameParserException("Substitution expression was not closed.");
		}

		// cut off substitution
		String subsitution = expression.substring(0, indexOfEndSub);
		String[] chunks = subsitution.split(",");

		if (chunks.length > 2 || chunks.length == 0) {
			throw new NameParserException("Too many arguments in substitution expression.");
		}

		try {
			chunks[0] = chunks[0].trim();
			// Normal subs
			if (chunks.length == 1) {
				return group(Integer.parseInt(chunks[0]));
			} else {

				chunks[1] = chunks[1].trim();
				// if eq. 04
				if (chunks[1].length() > 1 && chunks[1].substring(0, 1).equals("0")) {
					return group(Integer.parseInt(chunks[0]), '0', Integer.parseInt(chunks[1].substring(1)));
				} else {
					return group(Integer.parseInt(chunks[0]), ' ', Integer.parseInt(chunks[1]));
				}
			}

		} catch (NumberFormatException ex) {
			throw new NameParserException("Substitution expression consists letters or signs.");
		}

	}

	/**
	 * Method returns text builder which appends provided string to string builder.
	 * 
	 * @param string to be appedned to string builder.
	 * @return name builder.
	 */
	private static NameBuilder text(String string) {
		return (filterResult, sb) -> sb.append(string);
	}

	/**
	 * Method returns group builder which appends to string builder.
	 * 
	 * @param index of the group.
	 * @return group builder.
	 */
	private static NameBuilder group(int index) {
		return (filterResult, sb) -> {
			if (index > filterResult.numberOfGroups() || index < 0) {
				throw new IndexOutOfBoundsException("Substitition index out of bounds");
			}
			sb.append(filterResult.group(index));
		};
	}

	/**
	 * Method returns group builder which appends given group of filtered result to
	 * string builder. If length of string in group is less than minWidth padding is
	 * added.
	 * 
	 * @param index    of group in filtered result.
	 * @param padding  to be added if needed.
	 * @param minWidth of string in group
	 * @return group builder.
	 */
	private static NameBuilder group(int index, char padding, int minWidth) {

		return (filterResult, sb) -> {
			if (index > filterResult.numberOfGroups() || index < 0) {
				throw new IndexOutOfBoundsException("Substitition index out of bounds");
			}

			String replacement = filterResult.group(index);

			StringBuilder paddingBuilder = new StringBuilder();
			while (paddingBuilder.length() < (minWidth - replacement.length())) {
				paddingBuilder.append(padding);
			}
			sb.append(paddingBuilder);
			sb.append(replacement);

		};

	}

}
