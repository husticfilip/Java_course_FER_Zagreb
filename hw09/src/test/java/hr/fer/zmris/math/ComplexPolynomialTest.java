package hr.fer.zmris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;

public class ComplexPolynomialTest {

	@Test
	public void testRidOfZeros() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
				Complex.IM,Complex.ZERO,Complex.ONE,new Complex(0,0),Complex.ZERO
		});

		assertEquals(pol.getCoefficitent(0).getReal(), 0);
		assertEquals(pol.getCoefficitent(0).getImaginary(), 1);

		assertEquals(pol.getCoefficitent(1).getReal(), 0);
		assertEquals(pol.getCoefficitent(1).getImaginary(), 0);
		
		assertEquals(pol.getCoefficitent(2).getReal(), 1);
		assertEquals(pol.getCoefficitent(2).getImaginary(), 0);
		
		assertThrows(IndexOutOfBoundsException.class, ()->{
			pol.getCoefficitent(3);
		});
	}
	
	@Test
	public void testAllZeros() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
				Complex.ZERO,Complex.ZERO,Complex.ZERO
		});

		assertEquals(pol.getCoefficitent(0).getReal(), 0);
		assertEquals(pol.getCoefficitent(0).getImaginary(), 0);
		
		assertThrows(IndexOutOfBoundsException.class, ()->{
			pol.getCoefficitent(1);
		});
	}

	@Test
	public void testMultyply() {
		ComplexPolynomial pol = new ComplexPolynomial(
				new Complex[] { new Complex(2, 0), Complex.IM, new Complex(2.5, -2), new Complex(1, 1) });
		ComplexPolynomial result = pol.multiply(new ComplexPolynomial(
				new Complex[] { new Complex(0, -4), new Complex(1.1, -2.2), new Complex(1, -4) }));

		assertEquals(0, result.getCoefficitent(0).getReal());
		assertEquals(-8, result.getCoefficitent(0).getImaginary());

		assertEquals(6.2, result.getCoefficitent(1).getReal(), 0.001);
		assertEquals(-4.4, result.getCoefficitent(1).getImaginary(), 0.001);

		assertEquals(-3.8, result.getCoefficitent(2).getReal(), 0.001);
		assertEquals(-16.9, result.getCoefficitent(2).getImaginary(), 0.001);

		assertEquals(6.35, result.getCoefficitent(3).getReal(), 0.001);
		assertEquals(-10.7, result.getCoefficitent(3).getImaginary(), 0.001);

		assertEquals(-2.2, result.getCoefficitent(4).getReal(), 0.001);
		assertEquals(-13.1, result.getCoefficitent(4).getImaginary(), 0.001);

		assertEquals(5, result.getCoefficitent(5).getReal(), 0.001);
		assertEquals(-3, result.getCoefficitent(5).getImaginary(), 0.001);
	}

	@Test
	public void testMultyplyWithSomeZeroCeoff() {
		ComplexPolynomial pol = new ComplexPolynomial(
				new Complex[] { new Complex(2, 0), Complex.IM, Complex.ZERO, new Complex(1, 1) });
		ComplexPolynomial result = pol.multiply(
				new ComplexPolynomial(new Complex[] { Complex.ZERO, new Complex(1.1, -2.2), new Complex(1, -4) }));

		assertEquals(0, result.getCoefficitent(0).getReal());
		assertEquals(0, result.getCoefficitent(0).getImaginary());

		assertEquals(2.2, result.getCoefficitent(1).getReal(), 0.001);
		assertEquals(-4.4, result.getCoefficitent(1).getImaginary(), 0.001);

		assertEquals(4.2, result.getCoefficitent(2).getReal(), 0.001);
		assertEquals(-6.9, result.getCoefficitent(2).getImaginary(), 0.001);

		assertEquals(4, result.getCoefficitent(3).getReal(), 0.001);
		assertEquals(1, result.getCoefficitent(3).getImaginary(), 0.001);

		assertEquals(3.3, result.getCoefficitent(4).getReal(), 0.001);
		assertEquals(-1.1, result.getCoefficitent(4).getImaginary(), 0.001);

		assertEquals(5, result.getCoefficitent(5).getReal(), 0.001);
		assertEquals(-3, result.getCoefficitent(5).getImaginary(), 0.001);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			result.getCoefficitent(6);
		});
		
		assertThrows(IndexOutOfBoundsException.class, ()->{
			pol.getCoefficitent(7);
		});
	}

	@Test
	public void testMultyplyWithZeroAtEnd() {
		ComplexPolynomial pol = new ComplexPolynomial(
				new Complex[] { new Complex(2, 10), new Complex(-12.2, 2.12), new Complex(2.5, -2), Complex.ZERO });
		ComplexPolynomial result = pol.multiply(
				new ComplexPolynomial(new Complex[] { Complex.ONE_NEG, new Complex(1.1, -2.2), new Complex(1, -4) }));

		assertEquals(-2, result.getCoefficitent(0).getReal());
		assertEquals(-10, result.getCoefficitent(0).getImaginary());

		assertEquals(36.4, result.getCoefficitent(1).getReal(), 0.001);
		assertEquals(4.48, result.getCoefficitent(1).getImaginary(), 0.001);

		assertEquals(30.744, result.getCoefficitent(2).getReal(), 0.001);
		assertEquals(33.172, result.getCoefficitent(2).getImaginary(), 0.001);

		assertEquals(-5.37, result.getCoefficitent(3).getReal(), 0.001);
		assertEquals(43.22, result.getCoefficitent(3).getImaginary(), 0.001);

		assertEquals(-5.5, result.getCoefficitent(4).getReal(), 0.001);
		assertEquals(-12, result.getCoefficitent(4).getImaginary(), 0.001);
	}

	@Test
	public void testDerive() {
		ComplexPolynomial pol = new ComplexPolynomial(
				new Complex[] { new Complex(1, 0), new Complex(5, 0), new Complex(2, 0), new Complex(7, 2) });
		ComplexPolynomial derived = pol.derive();

		assertEquals(5, derived.getCoefficitent(0).getReal());
		assertEquals(0, derived.getCoefficitent(0).getImaginary());

		assertEquals(4, derived.getCoefficitent(1).getReal());
		assertEquals(0, derived.getCoefficitent(1).getImaginary());

		assertEquals(21, derived.getCoefficitent(2).getReal());
		assertEquals(6, derived.getCoefficitent(2).getImaginary());

		assertThrows(IndexOutOfBoundsException.class, () -> {
			derived.getCoefficitent(3);
		});
	}

	@Test
	public void testDerive2() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] { new Complex(1, 0), new Complex(5, 0),
				Complex.IM_NEG, new Complex(2.1, -1.12), Complex.ZERO, new Complex(-0.5, 2) });
		ComplexPolynomial derived = pol.derive();

		assertEquals(5, derived.getCoefficitent(0).getReal());
		assertEquals(0, derived.getCoefficitent(0).getImaginary());

		assertEquals(0, derived.getCoefficitent(1).getReal());
		assertEquals(-2, derived.getCoefficitent(1).getImaginary());

		assertEquals(6.3, derived.getCoefficitent(2).getReal(), 0.001);
		assertEquals(-3.36, derived.getCoefficitent(2).getImaginary(), 0.001);

		assertEquals(0, derived.getCoefficitent(3).getReal());
		assertEquals(0, derived.getCoefficitent(3).getImaginary());

		assertEquals(-2.5, derived.getCoefficitent(4).getReal(), 0.001);
		assertEquals(10, derived.getCoefficitent(4).getImaginary(), 0.001);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			derived.getCoefficitent(5);
		});
	}
	
	@Test
	public void testDeriveZero() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] { new Complex(0, 0), Complex.ZERO});
		ComplexPolynomial derived = pol.derive();

		assertEquals(0, derived.getCoefficitent(0).getReal());
		assertEquals(0, derived.getCoefficitent(0).getImaginary());
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			derived.getCoefficitent(1);
		});
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
			new Complex(2,0),new Complex(0,1), new Complex(2.5,-2),new Complex(1,1)
		});
		
		Complex result = pol.apply(new Complex(-0.5,2.2));
		
		assertEquals(0.058, result.getReal(),0.001);
		assertEquals(1.317, result.getImaginary(),0.001);
	}
	
	@Test
	public void testApplyWithZeroInCoeff() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
			new Complex(2,0),new Complex(0,1),Complex.ZERO, new Complex(2.5,-2),new Complex(1,1),Complex.ZERO
		});
		
		Complex result = pol.apply(new Complex(-0.5,2.2));
		
		assertEquals(-4.3264, result.getReal(),0.001);
		assertEquals(-0.8409, result.getImaginary(),0.001);
	}
	
	@Test
	public void testApplyWitZero() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
			new Complex(2,0),new Complex(0,1),Complex.ZERO, new Complex(2.5,-2),new Complex(1,1),Complex.ZERO
		});
		
		Complex result = pol.apply(new Complex(0,0));
		
		assertEquals(2, result.getReal(),0.001);
		assertEquals(0, result.getImaginary(),0.001);
	}
	
	@Test
	public void testApplyWitZero2() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex[] {
			new Complex(2,0),Complex.ZERO
		});
		
		Complex result = pol.apply(new Complex(5,5));
		
		assertEquals(2, result.getReal(),0.001);
		assertEquals(0, result.getImaginary(),0.001);
	}
}