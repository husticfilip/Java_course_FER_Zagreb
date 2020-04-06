package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	ComplexNumber complexNumber;

	@Test
	public void testConstructor() {

		complexNumber = new ComplexNumber(1, -1);
		assertEquals(1, complexNumber.getReal());
		assertEquals(-1, complexNumber.getImaginary());
	}

	@Test
	public void formVariations() {
		complexNumber = new ComplexNumber(0, 0);

		// Just real
		ComplexNumber cn = ComplexNumber.formReal(2.12);
		assertEquals(2.12, cn.getReal());
		assertEquals(0, cn.getImaginary());

		// Just imaginary
		cn = ComplexNumber.formImaginary(2.12);
		assertEquals(0, cn.getReal());
		assertEquals(2.12, cn.getImaginary());
	}

	@Test
	public void testMaginitudeAndAnglesOfAxes() {
		complexNumber = new ComplexNumber(0, 0);
		ComplexNumber cn;

		cn = ComplexNumber.fromMagnitudeAndAngle(5, 0);
		assertEquals(5, cn.getReal());
		assertEquals(0, cn.getImaginary());

		cn = ComplexNumber.fromMagnitudeAndAngle(5, 2 * Math.PI);
		assertEquals(5, cn.getReal());
		assertEquals(0, cn.getImaginary());

		cn = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI / 2);
		assertEquals(0, cn.getReal());
		assertEquals(5, cn.getImaginary());

		cn = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI);
		assertEquals(-5, cn.getReal());
		assertEquals(0, cn.getImaginary());

		cn = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI * 3 / 2);
		assertEquals(0, cn.getReal());
		assertEquals(-5, cn.getImaginary());
	}

	@Test
	public void testMagnitudeAndAnglesOfPi4() {
		complexNumber = new ComplexNumber(0, 0);
		ComplexNumber cn;

		cn = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI / 4);
		assertEquals(1, cn.getReal(), 0.001);
		assertEquals(1, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI * 3 / 4);
		assertEquals(-1, Math.round(cn.getReal()), 0.001);
		assertEquals(1, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI * 5 / 4);
		assertEquals(-1, cn.getReal(), 0.001);
		assertEquals(-1, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI * 7 / 4);
		assertEquals(1, cn.getReal(), 0.001);
		assertEquals(-1, cn.getImaginary(), 0.001);
	}

	@Test
	public void testOtherMagnitudeAndAnglesAngles() {

		complexNumber = new ComplexNumber(0, 0);
		ComplexNumber cn;

		cn = ComplexNumber.fromMagnitudeAndAngle(2.415, 1.09788);
		assertEquals(1.1, cn.getReal(), 0.001);
		assertEquals(2.15, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(2.415, 2.0437);
		assertEquals(-1.1, cn.getReal(), 0.001);
		assertEquals(2.15, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(2.415, 4.23948);
		assertEquals(-1.1, cn.getReal(), 0.001);
		assertEquals(-2.15, cn.getImaginary(), 0.001);

		cn = ComplexNumber.fromMagnitudeAndAngle(2.415, 5.18529);
		assertEquals(1.1, cn.getReal(), 0.001);
		assertEquals(-2.15, cn.getImaginary(), 0.001);
	}

	@Test
	public void testMagnitude0andNegative() {
		complexNumber = new ComplexNumber(0, 0);
		ComplexNumber cn;

		cn = ComplexNumber.fromMagnitudeAndAngle(0, Math.PI);
		assertEquals(0, cn.getReal(), 0.001);
		assertEquals(0, cn.getImaginary(), 0.001);

		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.fromMagnitudeAndAngle(-1, Math.PI);
		});
	}

	@Test
	public void testAngleTransformMethod() {
		complexNumber = new ComplexNumber(0, 0);

		assertEquals(0, ComplexNumber.angleTransformBetween0and2Pi(0));
		assertEquals(Math.PI, ComplexNumber.angleTransformBetween0and2Pi(Math.PI));
		assertEquals(2 * Math.PI, ComplexNumber.angleTransformBetween0and2Pi(2 * Math.PI));
		assertEquals(Math.PI, ComplexNumber.angleTransformBetween0and2Pi(3 * Math.PI));
		assertEquals(Math.PI / 2, ComplexNumber.angleTransformBetween0and2Pi(Math.PI * 5 / 2));
		assertEquals(Math.PI * 7 / 4, ComplexNumber.angleTransformBetween0and2Pi(-Math.PI * 1 / 4));
	}

	@Test
	public void testGoodParsing() {
		complexNumber = new ComplexNumber(0, 0);
		// just i
		ComplexNumber cn = ComplexNumber.parse("i");
		assertEquals(0, cn.getReal());
		assertEquals(1, cn.getImaginary());
		cn = ComplexNumber.parse("-i");
		assertEquals(0, cn.getReal());
		assertEquals(-1, cn.getImaginary());

		// imaginary
		cn = ComplexNumber.parse("1.11i");
		assertEquals(0, cn.getReal());
		assertEquals(1.11, cn.getImaginary());
		cn = ComplexNumber.parse("-1.11i");
		assertEquals(0, cn.getReal());
		assertEquals(-1.11, cn.getImaginary());
		cn = ComplexNumber.parse("+1.11i");
		assertEquals(0, cn.getReal());
		assertEquals(1.11, cn.getImaginary());

		// real
		cn = ComplexNumber.parse("1.11");
		assertEquals(1.11, cn.getReal());
		assertEquals(0, cn.getImaginary());
		cn = ComplexNumber.parse("+1.11");
		assertEquals(1.11, cn.getReal());
		assertEquals(0, cn.getImaginary());
		cn = ComplexNumber.parse("-1.11");
		assertEquals(-1.11, cn.getReal());
		assertEquals(0, cn.getImaginary());

		// combination of 1 and i
		cn = ComplexNumber.parse("1+i");
		assertEquals(1, cn.getReal());
		assertEquals(1, cn.getImaginary());
		cn = ComplexNumber.parse("-1-i");
		assertEquals(-1, cn.getReal());
		assertEquals(-1, cn.getImaginary());

		// combination of real and imaginary
		cn = ComplexNumber.parse("2.22+1.11i");
		assertEquals(2.22, cn.getReal());
		assertEquals(1.11, cn.getImaginary());
		cn = ComplexNumber.parse("-2.22+1.11i");
		assertEquals(-2.22, cn.getReal());
		assertEquals(1.11, cn.getImaginary());
		cn = ComplexNumber.parse("+2.22+1.11i");
		assertEquals(2.22, cn.getReal());
		assertEquals(1.11, cn.getImaginary());
		cn = ComplexNumber.parse("2.22-1.11i");
		assertEquals(2.22, cn.getReal());
		assertEquals(-1.11, cn.getImaginary());
		cn = ComplexNumber.parse("-2.22-1.11i");
		assertEquals(-2.22, cn.getReal());
		assertEquals(-1.11, cn.getImaginary());
	}

	@Test
	public void badParsing() {
		complexNumber = new ComplexNumber(0, 0);

		// too many signs for imaginary
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("--i");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-i");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("++i");
		});

		// to many signs for real
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("++2.22");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("--2.22");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("-+2.22");
		});

		// i in front
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("i2.22");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("-i2.22");
		});

		// too many signs
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+2.71-+3.15i");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-2.71-3.15i");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("++2.71--3.15i");
		});

		// NAN
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("2.2.222i");
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("2.2.222");
		});
	}

	@Test
	public void testParsingNull() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber.parse(null);
		});
	}

	@Test
	public void testMagintude() {
		complexNumber = new ComplexNumber(2, 0);
		assertEquals(2, complexNumber.getMagnitude());

		complexNumber = new ComplexNumber(0, 2);
		assertEquals(2, complexNumber.getMagnitude());

		complexNumber = new ComplexNumber(5, 2);
		assertEquals(Math.sqrt(29), complexNumber.getMagnitude(), 0.001);

		complexNumber = new ComplexNumber(2, 5);
		assertEquals(Math.sqrt(29), complexNumber.getMagnitude(), 0.001);

		ComplexNumber cn = ComplexNumber.fromMagnitudeAndAngle(20, 1.25);
		assertEquals(20, cn.getMagnitude(), 0.001);
	}

	@Test
	public void testAngle() {
		complexNumber = new ComplexNumber(1, 1);
		assertEquals(Math.PI / 4, complexNumber.getAngle(), 0.001);

		ComplexNumber cn = ComplexNumber.fromMagnitudeAndAngle(20, 1.25);
		assertEquals(1.25, cn.getAngle(), 0.001);

		// angles on axes
		complexNumber = new ComplexNumber(1, 0);
		assertEquals(0, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(-1, 0);
		assertEquals(Math.PI, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(0, 1);
		assertEquals(Math.PI / 2, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(0, -1);
		assertEquals(Math.PI * 3 / 2, complexNumber.getAngle(), 0.001);

		// angles in all 4 quadrants
		complexNumber = new ComplexNumber(1, 1);
		assertEquals(Math.PI / 4, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(-1, 1);
		assertEquals(Math.PI * 3 / 4, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(-1, -1);
		assertEquals(Math.PI * 5 / 4, complexNumber.getAngle(), 0.001);
		complexNumber = new ComplexNumber(1, -1);
		assertEquals(Math.PI * 7 / 4, complexNumber.getAngle(), 0.001);
	}

	@Test
	public void testAdding() {
		complexNumber = new ComplexNumber(1, 1);

		ComplexNumber cn = complexNumber.add(new ComplexNumber(50, -2));
		assertEquals(51, cn.getReal());
		assertEquals(-1, cn.getImaginary());

		cn = complexNumber.add(new ComplexNumber(-50, 2));
		assertEquals(-49, cn.getReal());
		assertEquals(3, cn.getImaginary());

		assertThrows(NullPointerException.class, () -> {
			complexNumber.add(null);
		});
	}

	@Test
	public void testSub() {
		complexNumber = new ComplexNumber(1, 1);

		ComplexNumber cn = complexNumber.sub(new ComplexNumber(50, -2));
		assertEquals(-49, cn.getReal());
		assertEquals(3, cn.getImaginary());

		cn = complexNumber.sub(new ComplexNumber(-50, 2));
		assertEquals(51, cn.getReal());
		assertEquals(-1, cn.getImaginary());

		assertThrows(NullPointerException.class, () -> {
			complexNumber.sub(null);
		});
	}

	@Test
	public void testMultiplication() {
		complexNumber = new ComplexNumber(1, 2);

		ComplexNumber cn = complexNumber.mul(new ComplexNumber(3, 4));
		assertEquals(-5, cn.getReal());
		assertEquals(10, cn.getImaginary());

		cn = complexNumber.mul(new ComplexNumber(0, 0));
		assertEquals(0, cn.getReal());
		assertEquals(0, cn.getImaginary());

		cn = complexNumber.mul(new ComplexNumber(1, 0));
		assertEquals(1, cn.getReal());
		assertEquals(2, cn.getImaginary());

		cn = complexNumber.mul(new ComplexNumber(0, 1));
		assertEquals(-2, cn.getReal());
		assertEquals(1, cn.getImaginary());

		complexNumber = new ComplexNumber(0, 2);
		cn = complexNumber.mul(new ComplexNumber(0, 1));
		assertEquals(-2, cn.getReal());
		assertEquals(0, cn.getImaginary());

		assertThrows(NullPointerException.class, () -> {
			complexNumber.mul(null);
		});
	}

	@Test
	public void testDivision() {
		complexNumber = new ComplexNumber(1, 2);

		ComplexNumber cn = complexNumber.div(new ComplexNumber(3, 4));
		assertEquals((double) 11 / 25, cn.getReal(), 0.001);
		assertEquals((double) 2 / 25, cn.getImaginary(), 0.001);

		cn = complexNumber.div(new ComplexNumber(3, -4));
		assertEquals((double) -1 / 5, cn.getReal(), 0.001);
		assertEquals((double) 2 / 5, cn.getImaginary(), 0.001);

		cn = complexNumber.div(new ComplexNumber(-3, 4));
		assertEquals((double) 1 / 5, cn.getReal(), 0.001);
		assertEquals((double) -2 / 5, cn.getImaginary(), 0.001);

		cn = complexNumber.div(new ComplexNumber(-3, 0));
		assertEquals((double) -1 / 3, cn.getReal(), 0.001);
		assertEquals((double) -2 / 3, cn.getImaginary(), 0.001);

		cn = complexNumber.div(new ComplexNumber(0, -3));
		assertEquals((double) -2 / 3, cn.getReal(), 0.001);
		assertEquals((double) 1 / 3, cn.getImaginary(), 0.001);

		// exception
		assertThrows(IllegalArgumentException.class, () -> {
			complexNumber.div(new ComplexNumber(0, 0));
		});
		assertThrows(NullPointerException.class, () -> {
			complexNumber.div(null);
		});

		// one more example
		complexNumber = new ComplexNumber(2.2, -1.1);
		cn = complexNumber.div(new ComplexNumber(5.05, 2.2));
		assertEquals(0.2864, cn.getReal(), 0.001);
		assertEquals(-0.34259, cn.getImaginary(), 0.001);
	}

	@Test
	public void testPow() {
		complexNumber = new ComplexNumber(1, 1);

		// 1+i
		ComplexNumber cn = complexNumber.power(0);
		assertEquals(1, cn.getReal());
		assertEquals(0, cn.getImaginary());

		cn = complexNumber.power(1);
		assertEquals(1, cn.getReal(), 0.001);
		assertEquals(1, cn.getImaginary(), 0.001);

		cn = complexNumber.power(2);
		assertEquals(0, cn.getReal(), 0.001);
		assertEquals(2, cn.getImaginary(), 0.001);

		cn = complexNumber.power(20);
		assertEquals(-1024, cn.getReal(), 0.001);
		assertEquals(0, cn.getImaginary(), 0.001);

		// -1+i
		complexNumber = new ComplexNumber(-1, 1);
		cn = complexNumber.power(2);
		assertEquals(0, cn.getReal(), 0.001);
		assertEquals(-2, cn.getImaginary(), 0.001);

		cn = complexNumber.power(4);
		assertEquals(-4, cn.getReal(), 0.001);
		assertEquals(0, cn.getImaginary(), 0.001);

		// -1-i
		complexNumber = new ComplexNumber(-1, -1);
		cn = complexNumber.power(2);
		assertEquals(0, cn.getReal(), 0.001);
		assertEquals(2, cn.getImaginary(), 0.001);

		cn = complexNumber.power(4);
		assertEquals(-4, cn.getReal(), 0.001);
		assertEquals(0, cn.getImaginary(), 0.001);

		// 1-i
		complexNumber = new ComplexNumber(1, -1);
		cn = complexNumber.power(2);
		assertEquals(0, cn.getReal(), 0.001);
		assertEquals(-2, cn.getImaginary(), 0.001);

		cn = complexNumber.power(4);
		assertEquals(-4, cn.getReal(), 0.001);
		assertEquals(0, cn.getImaginary(), 0.001);

		// exception
		assertThrows(IllegalArgumentException.class, () -> {
			complexNumber.power(-1);
		});
	}

	@Test
	public void testPowWithSomeRationalNumbers() {
		complexNumber = new ComplexNumber(2.2, 1.1);

		// 2.2+1.1i
		ComplexNumber cn = complexNumber.power(3);
		assertEquals(2.662, cn.getReal(), 0.001);
		assertEquals(14.641, cn.getImaginary(), 0.001);

		// -2.2+1.1i
		complexNumber = new ComplexNumber(-2.2, 1.1);
		cn = complexNumber.power(4);
		assertEquals(-10.2487, cn.getReal(), 0.001);
		assertEquals(-35.1384, cn.getImaginary(), 0.001);

		// -2.2-1.1i
		complexNumber = new ComplexNumber(-2.2, -1.1);
		cn = complexNumber.power(1);
		assertEquals(-2.2, cn.getReal(), 0.001);
		assertEquals(-1.1, cn.getImaginary(), 0.001);

		cn = complexNumber.power(10);
		assertEquals(-614.7169, cn.getReal(), 0.001);
		assertEquals(-8082.10151, cn.getImaginary(), 0.001);

		// 2.2-1.1i
		complexNumber = new ComplexNumber(2.2, -1.1);
		cn = complexNumber.power(10);
		assertEquals(-614.7169, cn.getReal(), 0.001);
		assertEquals(8082.10151, cn.getImaginary(), 0.001);

	}

//	@Test
//	public void testRootWithSomeRationalNumbers() {
//		complexNumber = new ComplexNumber(2.2, 1.1);
//
//		// 2.2+1.1i
//		ComplexNumber cn[] = complexNumber.root(3);
//		
//
//		// -2.2+1.1i
//		complexNumber = new ComplexNumber(-2.2, 1.1);
//		cn = complexNumber.root(4);
//		
//
//		// -2.2-1.1i
//		complexNumber = new ComplexNumber(-2.2, -1.1);
//		cn = complexNumber.root(1);
//		
//		cn = complexNumber.root(5);
//	
//
//		// 2.2-1.1i
//		complexNumber = new ComplexNumber(2.2, -1.1);
//		cn = complexNumber.root(10);
//
//		// exception
//		assertThrows(IllegalArgumentException.class, () -> {
//			complexNumber.root(0);
//		});
//	}
	
	@Test
	public void testEquals() {
		ComplexNumber c1=new ComplexNumber(21.12, 12.12);
		ComplexNumber c2=new ComplexNumber(21.12, 12.12);
		assertEquals(true, c1.equals(c2));
		
		c1=new ComplexNumber(21.1200001, 12.12);
		c2=new ComplexNumber(21.12, 12.12);
		assertEquals(false, c1.equals(c2));

		c1=new ComplexNumber(1, 1);
		c2=new ComplexNumber(1, 1);
		assertEquals(true, c1.equals(c2));

	}
	
	@Test
	public void testToString() {
		ComplexNumber c = new ComplexNumber(-1, -2.21);
		System.out.println(c.toString());
	}

}
