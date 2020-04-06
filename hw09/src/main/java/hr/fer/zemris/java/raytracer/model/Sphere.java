package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * Class represents GraphicalObject Sphere.
 * 
 * @author Filip Hustić
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of sphere.
	 */
	private Point3D center;
	/**
	 * Radius of sphere.
	 */
	private double radius;
	/**
	 * Diffusion coefficient for red light source color.
	 */
	private double kdr;
	/**
	 * Diffusion coefficient for green light source color.
	 */
	private double kdg;
	/**
	 * Diffusion coefficient for blue light source color.
	 */
	private double kdb;
	/**
	 * Reflection coefficient for red light source color.
	 */
	private double krr;
	/**
	 * Reflection coefficient for green light source color.
	 */
	private double krg;
	/**
	 * Reflection coefficient for blue light source color.
	 */
	private double krb;
	/**
	 * Shininess factor of sphere.
	 */
	private double krn;

	/**
	 * Constructor.
	 * 
	 * @param center center of sphere.
	 * @param radius radius of sphere.
	 * @param kdr    diffusion coefficient for red light source color.
	 * @param kdg    diffusion coefficient for green light source color.
	 * @param kdb    diffusion coefficient for blue light source color.
	 * @param krr    reflection coefficient for red light source color.
	 * @param krg    reflection coefficient for green light source color.
	 * @param krb    reflection coefficient for blue light source color.
	 * @param krn    shininess factor of sphere.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double a = ray.direction.scalarProduct(ray.direction);
		double b = -2 * ray.direction.scalarProduct(center.sub(ray.start));
		Point3D difference = center.sub(ray.start);
		double c = difference.scalarProduct(difference) - Math.pow(radius, 2);

		double d = b * b - 4 * a * c;

		if (d < 0) {
			return null;
		}

		d = Math.sqrt(d);
		double t1 = (-b + d) / (2 * a);
		double t2 = (-b - d) / (2 * a);
		// smaller t is one closer to the start point of the ray.
		if (t1 > t2) {
			t1 = t2;
		}
		Point3D intersectionPoint = new Point3D(ray.start.x + ray.direction.x * t1, ray.start.y + ray.direction.y * t1,
				ray.start.z + ray.direction.z * t1);
		Point3D distanceVector = ray.start.sub(intersectionPoint);

		// if intersection is behind the eye
		if (distanceVector.scalarProduct(ray.direction) > 0) {
			return null;
		}

		double distance = distanceVector.norm();

		return new RayIntersection(intersectionPoint, distance, false) {

			@Override
			public Point3D getNormal() {
				return intersectionPoint.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};

	}

}
