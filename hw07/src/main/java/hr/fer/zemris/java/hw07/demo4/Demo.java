package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demo program which holds main method and methods for student record
 * manipulation.
 * 
 * @author Filip Hustić
 *
 */
public class Demo {

	/**
	 * Entrance point of program. Student records are read from studenti.txt and
	 * placed in list of StudentRecord.
	 * <p>
	 * Program outputs manipulations over list of StudentRecord.
	 * <p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("studenti.txt"));
			List<StudentRecord> records = new ArrayList<StudentRecord>();

			for (String line : lines) {
				String[] splited = line.split("\t");
				records.add(new StudentRecord(splited[0], splited[1], splited[2], Double.parseDouble(splited[3]),
						Double.parseDouble(splited[4]), Double.parseDouble(splited[5]), Integer.parseInt(splited[6])));
			}

			long broj = vratiBodovaViseOd25(records);
			System.out.println("Zadatak1");
			System.out.println("========");
			System.out.println(broj);

			long broj5 = vratiBrojOdlikasa(records);
			System.out.println();
			System.out.println("Zadatak2");
			System.out.println("========");
			System.out.println(broj5);

			List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
			System.out.println();
			System.out.println("Zadatak3");
			System.out.println("========");
			odlikasi.stream().forEach(System.out::println);

			List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
			System.out.println();
			System.out.println("Zadatak4");
			System.out.println("========");
			odlikasiSortirano.stream().forEach(System.out::println);

			List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
			System.out.println();
			System.out.println("Zadatak5");
			System.out.println("========");
			nepolozeniJMBAGovi.stream().forEach(System.out::println);

			Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
			System.out.println();
			System.out.println("Zadatak6");
			System.out.println("========");
			mapaPoOcjenama.entrySet().stream().forEach(s -> {
				System.out.println(s.getKey() + "=>");
				s.getValue().stream().forEach(System.out::println);
				System.out.println();
			});
			;

			Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
			System.out.println();
			System.out.println("Zadatak7");
			System.out.println("========");
			mapaPoOcjenama2.entrySet().stream().forEach(System.out::println);

			Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
			System.out.println();
			System.out.println("Zadatak8");
			System.out.println("========");
			prolazNeprolaz.entrySet().stream().forEach(s -> {
				System.out.println(s.getKey() + "=>");
				s.getValue().stream().forEach(System.out::println);
				System.out.println();
			});
			;
			;

			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method filters records and finds number of students with sum of points
	 * greater than 25.
	 * 
	 * @param records list of StudentRecord.
	 * @return number of students with sum of points greater than 25.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> (s.getBodoviMi() + s.getBodoviZi() + s.getBodoviMi()) > 25).count();
	}

	/**
	 * Method filters records and finds number of students with grade 5.
	 * 
	 * @param records list of StudentRecord.
	 * @return number of students with grade 5.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getOcijena() == 5).count();
	}

	/**
	 * Method filters records and collects list of students with grade 5.
	 * 
	 * @param records list of StudentRecord.
	 * @return list of students with grade 5.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getOcijena() == 5).collect(Collectors.toList());
	}

	/**
	 * Method filters records and collects list of students with grade 5 and sorts
	 * them by number points.
	 * 
	 * @param records list of StudentRecord.
	 * @return list of students with grade 5 sorted by number of points.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getOcijena() == 5)
				.sorted((s1, s2) -> Double.compare(s2.getBodoviMi() + s2.getBodoviZi() + s2.getBodoviLab(),
						s1.getBodoviMi() + s1.getBodoviZi() + s1.getBodoviLab()))
				.collect(Collectors.toList());
	}

	/**
	 * Method filters and collects list of students with grade 1.
	 * 
	 * @param records list of StudentRecord.
	 * @return list of students with grade 1.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getOcijena() == 1).map(StudentRecord::getJmbag)
				.sorted((j1, j2) -> j1.compareTo(j2)).collect(Collectors.toList());
	}

	/*
	 * Method collects records into map which contains new list of records. Each
	 * list is mapped to key with grade value which records in them have.
	 * 
	 * @param records list of StudentRecord.
	 * 
	 * @return map in which key is grade and value is list of students with that
	 * grade.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getOcijena));
	}

	/**
	 * Method collects records into map which contains number of records with same
	 * grade.
	 * 
	 * @param records list of StudentRecord.
	 * @return map in which key is grade and value is number of students with that
	 *         grade.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getOcijena, s -> 1, Math::addExact));
	}

	/**
	 * Method collects records into map which contains two keys and values assigned
	 * to them. One is for students with grade 1 and other is for all other
	 * students.
	 * 
	 * @param records list of StudentRecord.
	 * @return map in which key false holds list of students with grade 1, and key
	 *         true holds list of all other students.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(p -> p.getOcijena() > 1));
	}
}
