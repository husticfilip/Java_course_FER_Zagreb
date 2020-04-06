package hr.fer.zmris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class ComplexRootedPolynomialTest {

	@Test
	public void testApply() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, -1));
		Complex result = compRooted.apply(new Complex(3, 1));
		assertEquals(1, result.getReal());
		assertEquals(2, result.getImaginary());
	}

	@Test
	public void testApplyWithZeroConstant() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(Complex.ZERO, new Complex(2, -1), Complex.IM);
		Complex result = compRooted.apply(new Complex(3, 1));
		assertEquals(0, result.getReal());
		assertEquals(0, result.getImaginary());
	}

	@Test
	public void testApplyMultipleComplex() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, -1),
				Complex.IM_NEG);
		Complex result = compRooted.apply(new Complex(3, 1));
		assertEquals(-9, result.getReal());
		assertEquals(7, result.getImaginary());
	}

	@Test
	public void testApplyMultipleComplex2() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(2.2, 1.3), new Complex(2, -1),
				Complex.IM_NEG, Complex.ONE, Complex.IM_NEG, Complex.ONE_NEG);
		Complex result = compRooted.apply(new Complex(-5, 1));
		assertEquals(-8243, result.getReal());
		assertEquals(10729.9, result.getImaginary(), 0.001);
	}

	@Test
	public void testToComplexPol() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(2.2, 1.3), new Complex(2, -1),
				Complex.IM);

		ComplexPolynomial polynomial = compRooted.toComplexPolynom();

		assertEquals(-0.4, polynomial.getCoefficitent(0).getReal(), 0.001);
		assertEquals(5.7, polynomial.getCoefficitent(0).getImaginary(), 0.001);

		assertEquals(-4.4, polynomial.getCoefficitent(1).getReal(), 0.001);
		assertEquals(-2.6, polynomial.getCoefficitent(1).getImaginary(), 0.001);

		assertEquals(2.2, polynomial.getCoefficitent(2).getReal(), 0.001);
		assertEquals(1.3, polynomial.getCoefficitent(2).getImaginary(), 0.001);

		assertThrows(IndexOutOfBoundsException.class, () -> {
			polynomial.getCoefficitent(3);
		});
	}

	@Test
	public void testToComplexPol2() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(2.2, 1.3), new Complex(2, -1),
				Complex.IM,Complex.ZERO ,new Complex(-0.5, 2.2), Complex.ZERO);

		ComplexPolynomial polynomial = compRooted.toComplexPolynom();

		assertEquals(0, polynomial.getCoefficitent(0).getReal(), 0.001);
		assertEquals(0, polynomial.getCoefficitent(0).getImaginary(), 0.001);

		assertEquals(0, polynomial.getCoefficitent(1).getReal(), 0.001);
		assertEquals(0, polynomial.getCoefficitent(1).getImaginary(), 0.001);

		assertEquals(12.34, polynomial.getCoefficitent(2).getReal(), 0.001);
		assertEquals(3.73, polynomial.getCoefficitent(2).getImaginary(), 0.001);
		
		assertEquals(-8.32, polynomial.getCoefficitent(3).getReal(), 0.001);
		assertEquals(14.08, polynomial.getCoefficitent(3).getImaginary(), 0.001);

		assertEquals(-0.44, polynomial.getCoefficitent(4).getReal(), 0.001);
		assertEquals(-6.79, polynomial.getCoefficitent(4).getImaginary(), 0.001);
		
		assertEquals(2.2, polynomial.getCoefficitent(5).getReal(), 0.001);
		assertEquals(1.3, polynomial.getCoefficitent(5).getImaginary(), 0.001);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			polynomial.getCoefficitent(6);
		});
	}
	
	@Test
	public void testToComplexPolAllZeros() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(2.2, 1.3), Complex.ZERO ,Complex.ZERO);

		ComplexPolynomial polynomial = compRooted.toComplexPolynom();

		assertEquals(2.2, polynomial.getCoefficitent(2).getReal(), 0.001);
		assertEquals(1.3, polynomial.getCoefficitent(2).getImaginary(), 0.001);
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			polynomial.getCoefficitent(3);
		});
	}
	
//	@Test
//	public void testHomeworkExample() {
//		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
//				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
//				);
//				ComplexPolynomial cp = crp.toComplexPolynom();
//				System.out.println(crp);
//				System.out.println(cp);
//				System.out.println(cp.derive());
//
//	}
	
	@Test
	public void testIndexOfClosestRoot1() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(Complex.ONE,Complex.ONE,Complex.IM,new Complex(0,-0.9),new Complex(-0.8,-0.8),new Complex(-0.5,0),
				new Complex(0.2,0.2));
		
		assertEquals(5, compRooted.indexOfClosestRootFor(new Complex(0, 0),1.5));	
		
	}
	
	@Test
	public void testIndexOfClosestRoot2() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(Complex.ONE,Complex.IM_NEG,new Complex(-0.1,0.3),new Complex(0,0),new Complex(0.15,0.01),
				new Complex(-0.1,-0.1),new Complex(0.9,0.9));
		
		assertEquals(3, compRooted.indexOfClosestRootFor(new Complex(0.1, 0.1),0.3));	
		
	}
	
	
	@Test
	public void testNoClosestRoots() {
		ComplexRootedPolynomial compRooted = new ComplexRootedPolynomial(new Complex(3,-0.1),new Complex(3,-0.1), Complex.ZERO , new Complex(1.9,1.1), Complex.IM);
		
		assertEquals(-1, compRooted.indexOfClosestRootFor(new Complex(-1, -1.2),1.5));	
	}
}
