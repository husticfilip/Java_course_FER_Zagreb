package hr.fer.zmris.java.fractals.Newton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.fractals.Newton;
import hr.fer.zemris.math.Complex;

public class NewtonTest {

	@Test
	public void testRealNumber() {
		Complex complex=Newton.parseToComplex("64.21");
		assertEquals(64.21, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testRealNumber2() {
		Complex complex=Newton.parseToComplex("64");
		assertEquals(64, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testRealNumber3() {
		Complex complex=Newton.parseToComplex("6");
		assertEquals(6, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testZero() {
		Complex complex=Newton.parseToComplex("0.0000000000");
		assertEquals(0.0, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testRealMinusNumber() {
		Complex complex=Newton.parseToComplex("- 64.21");
		assertEquals(-64.21, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testImagNumber() {
		Complex complex=Newton.parseToComplex(" i64.21 ");
		assertEquals(0, complex.getReal());
		assertEquals(64.21, complex.getImaginary());
	}
	
	@Test
	public void testMinusImagNumber() {
		Complex complex=Newton.parseToComplex(" -i64.21 ");
		assertEquals(0, complex.getReal());
		assertEquals(-64.21, complex.getImaginary());
	}
	
	@Test
	public void testMinusImagNumber2() {
		Complex complex=Newton.parseToComplex(" -  i64");
		assertEquals(0, complex.getReal());
		assertEquals(-64, complex.getImaginary());
	}
	
	@Test
	public void testMinusImagNumber3() {
		Complex complex=Newton.parseToComplex(" -  i6");
		assertEquals(0, complex.getReal());
		assertEquals(-6, complex.getImaginary());
	}
	
	@Test
	public void testJustI() {
		Complex complex=Newton.parseToComplex("  i");
		assertEquals(0, complex.getReal());
		assertEquals(1, complex.getImaginary());
	}
	
	@Test
	public void testJustPlusI() {
		Complex complex=Newton.parseToComplex("  +i");
		assertEquals(0, complex.getReal());
		assertEquals(1, complex.getImaginary());
	}
	
	@Test
	public void testJustMinusI() {
		Complex complex=Newton.parseToComplex("  -i");
		assertEquals(0, complex.getReal());
		assertEquals(-1, complex.getImaginary());
	}
	
	@Test
	public void testJustMinusI2() {
		Complex complex=Newton.parseToComplex(" -  i");
		assertEquals(0, complex.getReal());
		assertEquals(-1, complex.getImaginary());
	}
	
	@Test
	public void testJustMinusIZero() {
		Complex complex=Newton.parseToComplex(" -  i0.0");
		assertEquals(0, complex.getReal());
		assertEquals(-0.0, complex.getImaginary());
	}
	
	@Test
	public void testRealAndPositiveImaginary() {
		Complex complex=Newton.parseToComplex(" 1.1 + i2.5");
		assertEquals(1.1, complex.getReal());
		assertEquals(2.5, complex.getImaginary());
	}
	
	@Test
	public void testRealAndPositiveImaginary2() {
		Complex complex=Newton.parseToComplex(" 3 + i1");
		assertEquals(3, complex.getReal());
		assertEquals(1, complex.getImaginary());
	}
	
	@Test
	public void testRealAndPositiveImaginary3() {
		Complex complex=Newton.parseToComplex(" -      111.12 +   i22.54");
		assertEquals(-111.12, complex.getReal());
		assertEquals(22.54, complex.getImaginary());
	}
	
	@Test
	public void testRealPlusAndPositiveImaginary3() {
		Complex complex=Newton.parseToComplex(" +111.12+i22.54");
		assertEquals(111.12, complex.getReal());
		assertEquals(22.54, complex.getImaginary());
	}
	
	@Test
	public void testRealAndPositiveImaginaryZeros() {
		Complex complex=Newton.parseToComplex(" 0.0 + i0");
		assertEquals(0, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	@Test
	public void testRealAndPositiveImaginaryZeros2() {
		Complex complex=Newton.parseToComplex(" 0.0 + i0.0");
		assertEquals(0, complex.getReal());
		assertEquals(0, complex.getImaginary());
	}
	
	@Test
	public void testRealAndNegImaginary() {
		Complex complex=Newton.parseToComplex(" 1.1 - i2.5");
		assertEquals(1.1, complex.getReal());
		assertEquals(-2.5, complex.getImaginary());
	}
	
	@Test
	public void testRealAndNegImaginary2() {
		Complex complex=Newton.parseToComplex(" 3 - i1");
		assertEquals(3, complex.getReal());
		assertEquals(-1, complex.getImaginary());
	}
	
	@Test
	public void testRealAndNegImaginary3() {
		Complex complex=Newton.parseToComplex(" -111.12 - i22.54");
		assertEquals(-111.12, complex.getReal());
		assertEquals(-22.54, complex.getImaginary());
	}
	@Test
	public void testRealAndNegImaginary4() {
		Complex complex=Newton.parseToComplex(" - 111.12 - i22.54  ");
		assertEquals(-111.12, complex.getReal());
		assertEquals(-22.54, complex.getImaginary());
	}
	
	@Test
	public void testRealAndNegImaginaryZeros() {
		Complex complex=Newton.parseToComplex(" 0.0 - i0 ");
		assertEquals(0, complex.getReal());
		assertEquals(-0.0, complex.getImaginary());
	}
	@Test
	public void testRealAndNegImaginaryZeros2() {
		Complex complex=Newton.parseToComplex(" 0.0 - i0.0  ");
		assertEquals(0, complex.getReal());
		assertEquals(-0.0, complex.getImaginary());
	}
	
	
	@Test
	public void testInvalid() {
		Complex complex=Newton.parseToComplex(" - i1..");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid2() {
		Complex complex=Newton.parseToComplex(" 1i");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid3() {
		Complex complex=Newton.parseToComplex(" 1  2");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid4() {
		Complex complex=Newton.parseToComplex(" 1-+i12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid5() {
		Complex complex=Newton.parseToComplex(" --1+i12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid6() {
		Complex complex=Newton.parseToComplex(" -1+i12   i12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid7() {
		Complex complex=Newton.parseToComplex("  i i12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid8() {
		Complex complex=Newton.parseToComplex("  i + i12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid9() {
		Complex complex=Newton.parseToComplex(" 1+b12");
		assertNull(complex);
	}
	
	@Test
	public void testInvalid10() {
		Complex complex=Newton.parseToComplex(" 12 + 12");
		assertNull(complex);
	}
}
