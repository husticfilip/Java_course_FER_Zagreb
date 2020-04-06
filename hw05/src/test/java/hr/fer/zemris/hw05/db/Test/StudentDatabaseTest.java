package hr.fer.zemris.hw05.db.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class StudentDatabaseTest {

//	@Test
//	public void testValidRecord() {
//		String[] records = new String[] { "0000000001	  Akšamovic	Marin	1", "0000000002	Bakamovic	Petra	5",
//				"0000000003	Bosnic	Andrea	4" };
//		StudentDatabase db = new StudentDatabase(records);
//
//		assertEquals(0, db.indexMap.get("0000000001"));
//		assertEquals(1, db.indexMap.get("0000000002"));
//		assertEquals(2, db.indexMap.get("0000000003"));
//	}

	@Test
	public void testDoubleJmbag() {
		String[] records = new String[] { "0000000001	Akšamovic	Marin	2", "0000000002	Bakamovic	Petra	3",
				"0000000002	Bosnic	Andrea	4" };

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	
	@Test
	public void testInvalidJmbag() {
		String[] records = new String[] { "1	Akšamovic	Marin	2", "0000000002	Bakamovic	Petra	3",
				"0000000002	Bosnic	Andrea	4" };

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	@Test
	public void testInvalidNumberOfArgs() {
		String[] records = new String[] { "1	Akšamovic	Marin	"};

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	@Test
	public void testInvalidNumberOfArgs2() {
		String[] records = new String[] { "1	Akšamovic	Marin 2 a	"};

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	@Test
	public void testInvalidGrade() {
		String[] records = new String[] { "1	Akšamovic	Marin -1"};

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	@Test
	public void testInvalidGrade2() {
		String[] records = new String[] { "1	Akšamovic	Marin 6"};

		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(records);
		});
	}
	
	@Test
	public void testForJmbag() {
		String[] records = new String[] { "0000000001	  Akšamovic	Marin	1", "0000000002	Bakamovic	Petra	5",
				"0000000003	Bosnic	Andrea	4" };
		StudentDatabase db = new StudentDatabase(records);
		
		assertEquals(new StudentRecord("0000000001", "Akšamovic", "Marin", 1), db.forJMBAG("0000000001"));
		assertEquals(new StudentRecord("0000000002", "Bakamovic", "Petra", 5), db.forJMBAG("0000000002"));
	}
	
	@Test
	public void testForJmbagUnexistingJmbag() {
		String[] records = new String[] { "0000000001	  Akšamovic	Marin	1", "0000000002	Bakamovic	Petra	5",
				"0000000003	Bosnic	Andrea	4" };
		StudentDatabase db = new StudentDatabase(records);
		
		assertEquals(null, db.forJMBAG("4312121222"));
	}
	
	@Test
	public void testFilter() {
		String[] records = new String[] { "0000000001	  Akšamovic	Marin	1", "0000000002	Bakamovic	Petra	5",
		"0000000003	Bosnic	Andrea	4" };
		StudentDatabase db = new StudentDatabase(records);

		List<StudentRecord> filteredRecords = db.filter((r)-> r.getJmbag().equals("0000000001") || r.getJmbag().equals("0000000003"));
		
		assertEquals(new StudentRecord("0000000001", "Akšamovic", "Marin", 1), filteredRecords.get(0));
		assertEquals(new StudentRecord("0000000003", "Bosnic", "Andrea", 4), filteredRecords.get(1));
	}
}
