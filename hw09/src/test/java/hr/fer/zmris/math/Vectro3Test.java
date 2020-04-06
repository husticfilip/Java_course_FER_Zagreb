package hr.fer.zmris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector3;

public class Vectro3Test {

	@Test
	public void testNorm() {
		Vector3 vector = new Vector3(-4, 1, 3);

		assertEquals(5.099, vector.norm(), 0.001);
	}

	@Test
	public void testNormalize() {
		Vector3 vector = new Vector3(-4.1, 1.5, 3).normalized();
		assertEquals(-0.7740, vector.getX(), 0.001);
		assertEquals(0.2831, vector.getY(), 0.001);
		assertEquals(0.5663, vector.getZ(), 0.001);
	}

	@Test
	public void testScalarProduct() {
		double scalar = new Vector3(-4.1, 1.5, 3).dot(new Vector3(1, 2, -1));
		assertEquals(-4.1, scalar, 0.001);
	}

	@Test
	public void testVectorProduct() {
		Vector3 vector = new Vector3(-4.1, 1.5, 3).cross(new Vector3(-1, 10.2, -100));
		assertEquals(-180.6, vector.getX(), 0.001);
		assertEquals(-413, vector.getY(), 0.001);
		assertEquals(-40.32, vector.getZ(), 0.001);
	}

	@Test
	public void testVectorScale() {
		Vector3 vector = new Vector3(-4.1, 1.5, 3).scale(2);
		assertEquals(-2.05, vector.getX(), 0.001);
		assertEquals(0.75, vector.getY(), 0.001);
		assertEquals(1.5, vector.getZ(), 0.001);
	}

	@Test
	public void homeworkExample() {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(l.norm());
		System.out.println(m);
		System.out.println(l.dot(j));
		System.out.println(i.add(new Vector3(0, 1, 0)).cosAngle(l));
	}

}
