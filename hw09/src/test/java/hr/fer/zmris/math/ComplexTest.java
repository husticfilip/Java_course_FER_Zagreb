package hr.fer.zmris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {

	@Test
	public void testPowerReal() {
		Complex comp = new Complex(10, 0);
		Complex pow = comp.power(3);
		assertEquals(1000, pow.getReal(), 0.001);
		assertEquals(0, pow.getImaginary(), 0.001);
	}

	@Test
	public void testPowerMinusReal() {
		Complex comp = new Complex(-10, 0);
		Complex pow = comp.power(3);
		assertEquals(-1000, pow.getReal(), 0.001);
		assertEquals(0, pow.getImaginary(), 0.001);
	}

	@Test
	public void testPowerImaginary() {
		Complex comp = new Complex(0, 2.2);
		Complex pow = comp.power(3);
		assertEquals(0, pow.getReal(), 0.001);
		assertEquals(-10.648, pow.getImaginary(), 0.001);
	}

	@Test
	public void testPowerMinusImaginary() {
		Complex comp = new Complex(0, -2.2);
		Complex pow = comp.power(3);
		assertEquals(0, pow.getReal(), 0.001);
		assertEquals(10.648, pow.getImaginary(), 0.001);
	}

	@Test
	public void testFirstQuadrant() {
		Complex comp = new Complex(1.1, 2.2);
		Complex pow = comp.power(3);
		assertEquals(-14.641, pow.getReal(), 0.001);
		assertEquals(-2.662, pow.getImaginary(), 0.001);
	}

	@Test
	public void testSecondQuadrant() {
		Complex comp = new Complex(1.1, 2.2);
		Complex pow = comp.power(3);
		assertEquals(-14.641, pow.getReal(), 0.001);
		assertEquals(-2.662, pow.getImaginary(), 0.001);
	}

	@Test
	public void testThirdQuadrant() {
		Complex comp = new Complex(-1.1, -2);
		Complex pow = comp.power(3);
		assertEquals(11.869, pow.getReal(), 0.001);
		assertEquals(0.74, pow.getImaginary(), 0.001);
	}

	@Test
	public void testFourthQuadrant() {
		Complex comp = new Complex(-1.1, 2);
		Complex pow = comp.power(4);
		assertEquals(-11.5759, pow.getReal(), 0.001);
		assertEquals(24.552, pow.getImaginary(), 0.001);
	}

	@Test
	public void testRoots() {
		List<Complex> roots = new Complex(2.2, -1.1).root(4);

		assertEquals(0.14484, roots.get(0).getReal(), 0.001);
		assertEquals(1.2439, roots.get(0).getImaginary(), 0.001);

		assertEquals(-1.2439, roots.get(1).getReal(), 0.001);
		assertEquals(0.14484, roots.get(1).getImaginary(), 0.001);

		assertEquals(-0.14484, roots.get(2).getReal(), 0.001);
		assertEquals(-1.2439, roots.get(2).getImaginary(), 0.001);

		assertEquals(1.2439, roots.get(3).getReal(),0.001);
		assertEquals(-0.14484, roots.get(3).getImaginary(),0.001);
	}
}
