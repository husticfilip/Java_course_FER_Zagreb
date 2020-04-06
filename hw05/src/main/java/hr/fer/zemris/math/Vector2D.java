package hr.fer.zemris.math;

import java.awt.IllegalComponentStateException;

/**
 * Class represents vector in 2 dimensional coordinate system.
 * 
 * @author Filip Hustić
 *
 */
public class Vector2D {

	/**
	 * X coordinate of vector.
	 */
	private double x;

	/**
	 * Y coordinate of vector.
	 */
	private double y;

	/**
	 * Default constructor.
	 * <p>
	 * It takes in x and y coordinate of vector.
	 * <p>
	 * 
	 * @param x coordinate of vector.
	 * @param y coordinate of vector.
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Method translates vector with given offset.
	 * <p>
	 * Vector offset holds value of offset in x direction in its x coordinate and
	 * offset in y direction in its y coordinate
	 * <p>
	 * 
	 * @param offset Vector which holds offset values for x and y coordinate.
	 */
	public void translate(Vector2D offset) {
		if (offset == null)
			throw new NullPointerException();

		this.x += offset.getX();
		this.y += offset.getY();
	}

	/**
	 * Method returns new vector which is result of translation of @this vector.
	 * <p>
	 * Vector offset holds value of offset in x direction in its x coordinate and
	 * offset in y direction in its y coordinate
	 * <p>
	 * 
	 * @param offset Vector which holds offset values for x and y coordinate.
	 * @return new vector which is result of translation of @this vector.
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null)
			throw new NullPointerException();

		return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
	}

	/**
	 * Method rotates @this vector by given angle.
	 * <p>
	 * Rotation is done by help of rotation matrix which loks like:
	 * | cos(φ)  sin(φ) 0|
	 * |-sin(φ)  cos(φ) 0|
	 * |  0        0    1|   
	 * <p>
	 * 
	 * <p>
	 * Vector represented in matrix form is one row matrix:
	 * |x   y   1| 
	 *  This represents homogeneous coordinate of vector. 
	 * <p>
	 * Coordinate of new vector (x',y') is calculated:
	 * 
	 * 							 | cos(φ)  sin(φ) 0|
	 *	|x' y' 1|= |x  y  1 | *	 |-sin(φ)  cos(φ) 0|
	 * 							 |  0        0    1|
	 * <p>
	 * 
	 * @param angle by which vector will be rotated.
	 */
	public void rotate(double angle) {
		double[][] vectorMatrix = { { this.x, this.y, 1 } };
		double rotationMatrix[][] = setRotationMatrix(angle);

		double[][] newVector = multiplyMatrices(vectorMatrix, rotationMatrix);
		this.x = newVector[0][0];
		this.y = newVector[0][1];
	}

	/**
	 * Method returns vector calculated by  rotation of 
	 *  @this vector by given angle.
	 * <p>
	 * Rotation is done by help of rotation matrix which loks like:
	 * | cos(φ)  sin(φ) 0|
	 * |-sin(φ)  cos(φ) 0|
	 * |  0        0    1|   
	 * <p>
	 * 
	 * <p>
	 * Vector represented in matrix form is one row matrix:
	 * |x   y   1| 
	 *  This represents homogeneous coordinate of vector. 
	 * <p>
	 * Coordinate of new vector (x',y') is calculated:
	 * 
	 * 							 | cos(φ)  sin(φ) 0|
	 *	|x' y' 1|= |x  y  1 | *	 |-sin(φ)  cos(φ) 0|
	 * 							 |  0        0    1|
	 * <p>
	 * 
	 * @param angle angle by which vector will be rotated.
	 * @return new vector which is result of rotation of @this vector.
	 */
	public Vector2D rotated(double angle) {
		double[][] vectorMatrix = { { this.x, this.y, 1 } };
		double rotationMatrix[][] = setRotationMatrix(angle);

		double[][] newVector = multiplyMatrices(vectorMatrix, rotationMatrix);
		return new Vector2D(newVector[0][0], newVector[0][1]);
	}

	/**
	 * Method which scales @this matrix by value of scaler.
	 * @param scaler the value by which scaling will be done.
	 */
	public void scale(double scaler) {
		if (scaler < 0)
			throw new IllegalArgumentException("Scaler can not be negative.");

		x = x * scaler;
		y = y * scaler;
	}

	/**
	 * Method which returns new vector as result of scaling @this 
	 * vector by value scaler.
	 * @param scaler scaler the value by which scaling will be done.
	 * @return new vector as result of scaling @this 
	 *		  vector by value scaler
	 */
	public Vector2D scaled(double scaler) {
		if (scaler < 0)
			throw new IllegalArgumentException("Scaler can not be negative.");

		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Method returns copy of @this vector.
	 * @return copy of @this vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Method initializes rotation Matrix.
	 * <p>
	 * It first looks up if angle is between 
	 * 0 and 2Pi and if not it sets it in that 
	 * range.
	 * <p>
	 * <p>
	 * Secondly it sets cos to 0 if angle is Pi/2 or 3Pi/2 
	 * because of mistakes while calculating with doubles.->
	 * Math.cos(Math.PI/2) does not return clear 0.
	 * <p>
	 * <p>
	 * Same is done with sin but for angles of Pi and 2Pi
	 * <p>
	 * @param angle by which vector will be rotated. 
	 * @return rotation matrix in form:
	 * | cos(φ)  sin(φ) 0|
	 * |-sin(φ)  cos(φ) 0|
	 * |  0        0    1| 
	 */
	private double[][] setRotationMatrix(double angle) {

		while (angle < 0) {
			angle += (Math.PI * 2);
		}
		while (angle > Math.PI * 2) {
			angle -= (Math.PI * 2);
		}

		double cos;
		double sin;

		// if angle is Pi/2 or 3Pi/2 set cos to 0.
		if ((Math.abs(angle - Math.PI / 2) < 1e-4) || (Math.abs(angle - Math.PI * 3 / 2) < 1e-4)) {
			cos = 0;
		} else {
			cos = Math.cos(angle);
		}

		// if angle is Pi/2 or 3Pi/2 set sin to 0.
		if ((Math.abs(angle - Math.PI) < 1e-4) || (Math.abs(angle - 2 * Math.PI) < 1e-4)) {
			sin = 0;
		} else {
			sin = Math.sin(angle);
		}

		double[][] rotationMatrix = { { cos, sin, 0 }, { -sin, cos, 0 }, { 0, 0, 1 } };
		return rotationMatrix;
	}

	/**
	 * 
	 * @return x coordinate of vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return y coordinate of vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Helper method which multiplies matrices and returns new matrix.
	 * <p>
	 * Method checks if number of columns of first matrix is equal to number of rows
	 * of second matrix. If it is multiplication is legal. Else exception is thrown.
	 * <p>
	 * 
	 * @param matrix1 first matrix.
	 * @param matrix2 second matrix.
	 * @return new matrix which is product of two passed
	 * @throws IllegalComponentStateException if matrix1 number of column is not
	 *                                        same as matrix2 number of rows.
	 */
	private double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
		if (matrix1[0].length != matrix2.length) {
			throw new IllegalComponentStateException(
					"Width of first matrix must be the same as height of secound matrix");
		}
		double[][] resultMatrix = new double[matrix1.length][matrix2[0].length];

		for (int i = 0; i < resultMatrix.length; ++i) {
			for (int j = 0; j < resultMatrix[0].length; ++j) {

				double result = 0;
				for (int x = 0; x < matrix2.length; ++x) {
					result += matrix1[i][x] * matrix2[x][j];
				}
				resultMatrix[i][j] = result;
			}
		}

		return resultMatrix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(this.x - other.x) > 1e-4)
			return false;
		if (Math.abs(this.y - other.y) > 1e-4)
			return false;
		return true;
	}

}
