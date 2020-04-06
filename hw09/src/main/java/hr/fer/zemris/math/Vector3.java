package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class represents 3D vector.
 * 
 * @author Filip Hustić
 *
 */
public class Vector3 {

	/**
	 * x coordinate of vector.
	 */
	private final double x;
	/**
	 * x coordinate of vector.
	 */
	private final double y;
	/**
	 * x coordinate of vector.
	 */
	private final double z;

	/**
	 * Default constructor which takes in x, y and z coordinate of vector.
	 * 
	 * @param x coordinate.
	 * @param y coordinate.
	 * @param z coordinate.
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Method calculates and returns norm of vector.
	 * 
	 * @return norm of vector.
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	/**
	 * Method returns new vector3 with normalized coordinates of @this vector.
	 * 
	 * @return new vector3 with normalized coordinate of @this vector.
	 */
	public Vector3 normalized() {
		double norm = this.norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Method adds components of given vector with @this vector and returns result
	 * in form of new vector.
	 * 
	 * @param other vector to be added with @this vector.
	 * @return new vector of which components are made by adding other vectro
	 *         with @this vector
	 * @throws NullPointerException if other vector is null
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Method subs components of @this vector with components of given vector and
	 * returns result in form of new vector.
	 * 
	 * @param other vector to be sub from @this vector.
	 * @return new vector of which components are made by sub of sub other vector
	 *         from @this vector.
	 * @throws NullPointerException if other vector is null
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Method returns scalar product of @this vector and given vector.
	 * 
	 * @param other vector whit which scalar product will be calculated.
	 * @return scalar product of two vectors.
	 * @throws NullPointerException if other vector is null
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Method returns new vector which is result of vector cross product of @this
	 * vector with given vector. Order of operation is : thisVector x otherVector.
	 * 
	 * @param other vector with which @this vector will calculate cross product
	 * @return new vector which is result of vector product between @this vector and
	 *         other vector.
	 * @throws NullPointerException if other vector is null
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		double xc = this.y * other.z - this.z * other.y;
		double yc = this.z * other.x - this.x * other.z;
		double zc = this.x * other.y - this.y * other.x;

		return new Vector3(xc, yc, zc);
	}

	/**
	 * Method returns new vector with coordinates of @this vector scaled,
	 * multiplied, by s.
	 * 
	 * @param s value by which coordinates of @this vector will be scaled.
	 * @return new vector with scaled coordinates of @this vector.
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);

	}

	/**
	 * Method returns cosinus of angle between @this vector and given vector.
	 * 
	 * @param other vector with which cosinus of angle is calculated.
	 * @return cosinus of angle between vectors.
	 * @throws NullPointerException if other vector is null
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);
		return dot(other) / (norm() * other.norm());
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
	 * 
	 * @return z coordinate of vector.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Method returns vector coordinates in from of array.
	 * <p>
	 * x coordinate is frist elemetn, y second and z third.
	 * <p>
	 * 
	 * @return vector coordinates in form of array.
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("( %.5f, %.5f, %.5f )", x, y, z);
	}

}
