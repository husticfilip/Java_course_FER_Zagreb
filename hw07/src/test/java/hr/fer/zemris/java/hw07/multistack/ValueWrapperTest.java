package hr.fer.zemris.java.hw07.multistack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ValueWrapperTest {

//	 @Test
//	 public void testHwExample() {
//		 ValueWrapper v1 = new ValueWrapper(null);
//		 ValueWrapper v2 = new ValueWrapper(null);
//		 v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
//		 ValueWrapper v3 = new ValueWrapper("1.2E1");
//		 ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
//		 v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
//		 ValueWrapper v5 = new ValueWrapper("12");
//		 ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
//		 v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
//		 ValueWrapper v7 = new ValueWrapper("Ankica");
//		 ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
//		 v7.add(v8.getValue()); // throws RuntimeException
//	 }
	
	@Test
	public void testAddingTwoDoubles() {
		ValueWrapper wrapper = new ValueWrapper(1.000001);
		wrapper.add(5.0);
		assertEquals(6.000001, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testAddingTwoIntegers() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.add(5);
		assertEquals(6, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testAddingTwoNulls() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.add(null);
		assertEquals(0, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testAddingIntegerAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.add(5.0001);
		assertEquals(6.0001, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testAddingIntegerAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.add(null);
		assertEquals(1, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testAddingDoubleAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.add(null);
		assertEquals(1.999, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testAddingDoubleStringDouble() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.add("1.0");
		assertEquals(2.999, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testAddingDoubleStringDouble2() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.add("1E1");
		assertEquals(11.999, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testAddingIntegerStringAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(3);
		wrapper.add("1");
		assertEquals(4, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testAddingIntegerStringAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(3.1);
		wrapper.add("1");
		assertEquals(4.1, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddingSomethingOther() {
		ValueWrapper wrapper = new ValueWrapper(3.1);
		assertThrows(RuntimeException.class, () -> {
			wrapper.add(new Float(1.1));
		});
	}

	@Test
	public void testInvalidString() {
		ValueWrapper wrapper = new ValueWrapper("aaaa");
		assertThrows(RuntimeException.class, () -> {
			wrapper.add(2);
		});
	}

	@Test
	public void testInvalidString2() {
		ValueWrapper wrapper = new ValueWrapper("1.1a");
		assertThrows(RuntimeException.class, () -> {
			wrapper.add(2);
		});
	}

	@Test
	public void testInvalidString3() {
		ValueWrapper wrapper = new ValueWrapper("1a");
		assertThrows(RuntimeException.class, () -> {
			wrapper.add(2);
		});
	}

	// Sub

	@Test
	public void testSubwoDoubles() {
		ValueWrapper wrapper = new ValueWrapper(1.000001);
		wrapper.subtract(5.0);
		assertEquals(-3.999999, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testSubTwoIntegers() {
		ValueWrapper wrapper = new ValueWrapper(11);
		wrapper.subtract(5);
		assertEquals(6, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testSubTwoNulls() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.subtract(null);
		assertEquals(0, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testSubIntegerAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.subtract(5.0001);
		assertEquals(-4.0001, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testSubIntegerAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.subtract(null);
		assertEquals(1, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testSubDoubleAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.subtract(null);
		assertEquals(1.999, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testSubDoubleStringDouble() {
		ValueWrapper wrapper = new ValueWrapper(1.1);
		wrapper.subtract("1.1");
		assertEquals(0.0, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testSubDoubleStringDouble2() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.subtract("1E1");
		assertEquals(-8.001, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testSubIntegerStringAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(3);
		wrapper.subtract("1");
		assertEquals(2, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testSubIntegerStringAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(3.1);
		wrapper.subtract("1");
		assertEquals(2.1, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}
	
	//MUL
	@Test
	public void testMulwoDoubles() {
		ValueWrapper wrapper = new ValueWrapper(1.1);
		wrapper.multiply(5.0);
		assertEquals(5.5, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testMulTwoIntegers() {
		ValueWrapper wrapper = new ValueWrapper(11);
		wrapper.multiply(5);
		assertEquals(55, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testMulTwoNulls() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.multiply(null);
		assertEquals(0, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testMulIntegerAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.multiply(5.0001);
		assertEquals(5.0001, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testMulIntegerAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.multiply(null);
		assertEquals(0, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testMulDoubleAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.multiply(null);
		assertEquals(0.0, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testMulDoubleStringDouble() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		wrapper.multiply("1.1");
		assertEquals(1.1, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testMulDoubleStringDouble2() {
		ValueWrapper wrapper = new ValueWrapper(1.1);
		wrapper.multiply("1E1");
		assertEquals(11.0, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testMulIntegerStringAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(3);
		wrapper.multiply("1");
		assertEquals(3, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testMulIntegerStringAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(3.1);
		wrapper.multiply("1");
		assertEquals(3.1, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}
	
	//DIVIDE
	@Test
	public void testDevwoDoubles() {
		ValueWrapper wrapper = new ValueWrapper(1.5);
		wrapper.divide(5.0);
		assertEquals(0.3, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testDivTwoIntegers() {
		ValueWrapper wrapper = new ValueWrapper(56);
		wrapper.divide(5);
		assertEquals(11, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}
	
	@Test
	public void testDivTwoIntegers2() {
		ValueWrapper wrapper = new ValueWrapper(58);
		wrapper.divide(5);
		assertEquals(12, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testDivIntegerAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.divide(5.000);
		assertEquals(0.2, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testDivIntegerAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1);
		wrapper.multiply(null);
		assertThrows(IllegalArgumentException.class, ()->{
			wrapper.divide(null);
		});
	}

	@Test
	public void testDivDoubleAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1.999);
		wrapper.multiply(null);
		assertThrows(IllegalArgumentException.class, ()->{
			wrapper.divide(null);
		});
	}

	@Test
	public void testDivDoubleStringDouble() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		wrapper.divide("1");
		assertEquals(1.0, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testDivDoubleStringDouble2() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		wrapper.divide("1E1");
		assertEquals(0.1, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	@Test
	public void testDevIntegerStringAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(3);
		wrapper.divide("1");
		assertEquals(3, wrapper.value);
		assertTrue(wrapper.value instanceof Integer);
	}

	@Test
	public void testDevIntegerStringAndDouble() {
		ValueWrapper wrapper = new ValueWrapper(4.4);
		wrapper.divide("2.2");
		assertEquals(2.0, wrapper.value);
		assertTrue(wrapper.value instanceof Double);
	}

	//NUM COMPARE
	@Test
	public void testNumCompareTwoDoubles() {
		ValueWrapper wrapper = new ValueWrapper(4.4);
		assertTrue(wrapper.numCompare(1.111) > 0);
	}
	
	@Test
	public void testNumCompareTwoSameDoubles() {
		ValueWrapper wrapper = new ValueWrapper(4.4);
		assertTrue(wrapper.numCompare(4.4) == 0);
	}
	
	@Test
	public void testNumCompareTwoIntegers() {
		ValueWrapper wrapper = new ValueWrapper(-4);
		assertTrue(wrapper.numCompare(4) < 0);
	}
	
	@Test
	public void testNumCompareTwoSameIntegers() {
		ValueWrapper wrapper = new ValueWrapper(-4);
		assertTrue(wrapper.numCompare(-4) == 0);
	}
	
	@Test
	public void testNumCompareTwoNulls() {
		ValueWrapper wrapper = new ValueWrapper(null);
		assertTrue(wrapper.numCompare(null) == 0);
	}
	
	@Test
	public void testNumDoubleAndInteger() {
		ValueWrapper wrapper = new ValueWrapper(1.1);
		assertTrue(wrapper.numCompare(1) > 0);
	}
	
	@Test
	public void testNumDoubleAndSameInteger() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		assertTrue(wrapper.numCompare(1) == 0);
	}
	
	@Test
	public void testNumDoubleAndIntegerString() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		assertTrue(wrapper.numCompare("1") == 0);
	}
	
	@Test
	public void testNumDoubleAndDoubleString() {
		ValueWrapper wrapper = new ValueWrapper(1.0);
		assertTrue(wrapper.numCompare("1.0") == 0);
	}
	@Test
	public void testNumDoubleAndNull() {
		ValueWrapper wrapper = new ValueWrapper(-1.0);
		assertTrue(wrapper.numCompare(null) < 0);
	}
	
	@Test
	public void testNumDoubleAndNullSame() {
		ValueWrapper wrapper = new ValueWrapper(0.0);
		assertTrue(wrapper.numCompare(null) == 0);
	}
	@Test
	public void testNumIntegerAndDoubleString() {
		ValueWrapper wrapper = new ValueWrapper(1);
		assertTrue(wrapper.numCompare("1.0") == 0);
	}
	
	@Test
	public void testNumIntegerAndNull() {
		ValueWrapper wrapper = new ValueWrapper(1);
		assertTrue(wrapper.numCompare(null) > 0);
	}
	
}