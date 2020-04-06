package hr.fer.zemris.java.hw05.db.Program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import hr.fer.zemris.java.hw05.db.Lexer.QueryParserException;

public class MainProgram {

	public static void main(String[] args) throws IOException {

//		List<String> lines = Files.readAllLines(Paths.get("db.txt"), StandardCharsets.UTF_8);
//		String[] records = (String[])lines.toArray();
		
		String docBody = new Loader().loader("db.txt");
		
		String[] records = docBody.split("\n");

		StudentDatabase db = new StudentDatabase(records);

		try (Scanner sc = new Scanner(System.in)) {

			while (true) {
				System.out.printf("> ");
				String query = sc.nextLine().trim();
				String possibleQuery = query.split(" ")[0];

				if (possibleQuery.equals("exit"))
					break;
				else if (!possibleQuery.equals("query"))
					throw new QueryParserException("");

				QueryParser parser = new QueryParser(query.substring(possibleQuery.length()));
				List<StudentRecord> rec = new ArrayList<StudentRecord>();
				if (parser.isDirectQuery()) {

					rec.add(db.forJMBAG(parser.getQueriedJMBAG()));
					System.out.println("Using index for record retrieval.");

				} else {

					for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
						rec.add(r);
					}
				}

				if (rec.size() == 0)
					System.out.println("Records selected: 0");
				else {
					outPrint(rec);
					System.out.println("Records selected: " + rec.size());
				}

			}
		} catch (QueryParserException ex) {
			System.out.println("Invalid query.Exiting.");
			return;
		}

		System.out.println("Goodbye!");
	}

	private static void outPrint(List<StudentRecord> records) {
		int lastNameLength = 0;
		int firstNameLegth = 0;
		int jmbagLegth = records.get(0).getJmbag().length();

		for (StudentRecord record : records) {
			if (record.getLastName().length() > lastNameLength)
				lastNameLength = record.getLastName().length();
			if (record.getFirstName().length() > firstNameLegth)
				firstNameLegth = record.getFirstName().length();
		}

		String jEq = "=".repeat(jmbagLegth + 1);
		String lEq = "=".repeat(lastNameLength + 2);
		String fEq = "=".repeat(firstNameLegth + 2);
		String gEq = "===";

		System.out.println("+" + jEq + "+" + lEq + "+" + fEq + "+" + gEq + "+");
		for (StudentRecord record : records) {
			int lastN = lastNameLength - record.getLastName().length() + 1;
			int firstN = firstNameLegth - record.getFirstName().length() + 1;

			String out = String.format("| %s | %s%" + lastN + "s| %s%" + firstN + "s| %d |", record.getJmbag(),
					record.getLastName(), " ", record.getFirstName(), " ", record.getFinalGrade());
			System.out.println(out);
		}
		System.out.println("+" + jEq + "+" + lEq + "+" + fEq + "+" + gEq + "+");

	}

}
