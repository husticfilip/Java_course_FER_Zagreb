package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class vector2DTest {

	@Test
	public void testNullOffsetTranslation() {
		Vector2D vector = new Vector2D(1, 2);

		assertThrows(NullPointerException.class, () -> {
			vector.translate(null);
		});
	}

	@Test
	public void testTranslation() {
		Vector2D vector = new Vector2D(0, 1);

		vector.translate(new Vector2D(0.2, -0.4));
		assertEquals(0.2, vector.getX(), 0.001);
		assertEquals(0.6, vector.getY(), 0.001);
	}

	@Test
	public void testTranslation2() {
		Vector2D vector = new Vector2D(-1, -1);

		vector.translate(new Vector2D(100.1, -99));
		assertEquals(99.1, vector.getX(), 0.001);
		assertEquals(-100, vector.getY(), 0.001);
	}

	@Test
	public void testTranslation3() {
		Vector2D vector = new Vector2D(-1.2, -1.2);

		vector.translate(new Vector2D(-1.5, -1.5));
		assertEquals(-2.7, vector.getX(), 0.001);
		assertEquals(-2.7, vector.getY(), 0.001);
	}

	@Test
	public void testTranslation4() {
		Vector2D vector = new Vector2D(-1.2, -1.2);

		vector.translate(new Vector2D(-1.5, -1.5));
		assertEquals(-2.7, vector.getX(), 0.001);
		assertEquals(-2.7, vector.getY(), 0.001);
	}

	@Test
	public void testTranslationed() {
		Vector2D vector = new Vector2D(-1.2, -1.2);

		Vector2D newVector = vector.translated(new Vector2D(-1.5, -1.5));
		assertEquals(-2.7, newVector.getX(), 0.001);
		assertEquals(-2.7, newVector.getY(), 0.001);
	}

	@Test
	public void testTranslatied2() {
		Vector2D vector = new Vector2D(-1, -1);

		Vector2D newVector = vector.translated(new Vector2D(100.1, -99));
		assertEquals(99.1, newVector.getX(), 0.001);
		assertEquals(-100, newVector.getY(), 0.001);
	}

	@Test
	public void rotateByPiHalf() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI / 2);
		assertEquals(new Vector2D(0, 1), vector);
		vector.rotate(Math.PI / 2);
		assertEquals(new Vector2D(-1, 0), vector);
		vector.rotate(Math.PI / 2);
		assertEquals(new Vector2D(0, -1), vector);
		vector.rotate(Math.PI / 2);
		assertEquals(new Vector2D(1, 0), vector);
	}

	@Test
	public void rotateByFivePiHalf() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI * 5 / 2);
		assertEquals(new Vector2D(0, 1), vector);
	}

	@Test
	public void rotateByPi() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI);
		assertEquals(new Vector2D(-1, 0), vector);
		vector.rotate(Math.PI);
		assertEquals(new Vector2D(1, 0), vector);
	}

	@Test
	public void rotateByTreePi() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI * 3);
		assertEquals(new Vector2D(-1, 0), vector);
		vector.rotate(Math.PI);
		assertEquals(new Vector2D(1, 0), vector);
	}

	@Test
	public void rotateByTwoPi() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI * 2);
		assertEquals(new Vector2D(1, 0), vector);
	}

	@Test
	public void rotateByMinusPiHalf() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(Math.PI * (-(double) 1 / 2));
		assertEquals(new Vector2D(0, -1), vector);
	}

	@Test
	public void rotateByMinusPi() {
		Vector2D vector = new Vector2D(1, 0);

		vector.rotate(-Math.PI);
		assertEquals(new Vector2D(-1, 0), vector);
	}

	@Test
	public void rotateByPiFour() {
		Vector2D vector= new Vector2D(1, 1);
		
		vector.rotate(Math.PI/4);
		assertEquals(new Vector2D(0,1.41421356237),vector);
	}
	
	@Test
	public void rotateByMinusPiFour() {
		Vector2D vector= new Vector2D(1, 1);
		
		vector.rotate(-Math.PI/4);
		assertEquals(new Vector2D(1.41421356237,0),vector);
	}
	
	@Test
	public void rotateByPiThird() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		vector.rotate(Math.PI/3);
		assertEquals(new Vector2D(-0.0490381056767,2.91506350946),vector);
	}
	
	@Test
	public void rotateByTwoPiThird() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		vector.rotate(Math.PI*2/3);
		assertEquals(new Vector2D(-2.54903810568,1.41506350946),vector);
	}

	@Test
	public void rotateByFivePiThird() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		vector.rotate(Math.PI*5/3);
		assertEquals(new Vector2D(2.54903810568,-1.41506350946),vector);
	}
	
	@Test
	public void testRotated() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		Vector2D rotated=vector.rotated(Math.PI*5/3);
		assertEquals(new Vector2D(2.54903810568,-1.41506350946),rotated);
	}
	
	@Test
	public void testScalerPositive() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		vector.scale(2);
		assertEquals(new Vector2D(5,3),vector);
	}
	
	@Test
	public void testScalerNegative() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		vector.scale((double)1/3);
		assertEquals(new Vector2D((double)5/6,0.5),vector);
	}
	
	@Test
	public void testScaled() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		
		Vector2D scaled=vector.scaled((double)1/3);
		assertEquals(new Vector2D((double)5/6,0.5),scaled);
	}
	
	@Test
	public void testCopy() {
		Vector2D vector= new Vector2D(2.5, 1.5);
		Vector2D copied= vector.copy();
		
		assertEquals(vector, copied);
		
	}
	
}
