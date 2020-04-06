package hr.fer.zemris.java.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

public class SphereTest {
	
	@Test
	public void testSphereRayIntersection() {
		Sphere sphere = new Sphere(new Point3D(0,0,0), 2, 1, 1, 1, 1, 1, 1, 1);
		RayIntersection intersection = sphere.findClosestRayIntersection(new Ray(new Point3D(5,0,0),new Point3D(-10,0,0)));
		
		assertEquals(2, intersection.getPoint().x);
		assertEquals(0, intersection.getPoint().y);
		assertEquals(0, intersection.getPoint().z);
		
		assertEquals(3, intersection.getDistance());
		
		Point3D normal = intersection.getNormal();
		assertEquals(1, normal.x);
		assertEquals(0, normal.y);
		assertEquals(0, normal.z);
	}

	
	@Test
	public void testSphereRayIntersection2() {
		Sphere sphere = new Sphere(new Point3D(1,4,0), 4, 1, 1, 1, 1, 1, 1, 1);
		RayIntersection intersection = sphere.findClosestRayIntersection(new Ray(new Point3D(1,2,3),new Point3D(1,0,-2)));
		
		assertEquals(0.77, intersection.getPoint().x,0.01);
		assertEquals(2, intersection.getPoint().y,0.01);
		assertEquals(3.46, intersection.getPoint().z,0.01);
		
		Point3D normal = intersection.getNormal();
		assertEquals(-0.0575, normal.x,0.01);
		assertEquals(-0.5, normal.y,0.01);
		assertEquals(0.865, normal.z,0.01);
		
		assertEquals(0.514296, intersection.getDistance(),0.01);
	}
	
	@Test
	public void testSphereRayIntersection3() {
		Sphere sphere = new Sphere(new Point3D(1,-1,2), 2, 1, 1, 1, 1, 1, 1, 1);
		RayIntersection intersection = sphere.findClosestRayIntersection(new Ray(new Point3D(0.5,1,-1),new Point3D(1,-2,1)));
		
		assertEquals(1.61 , intersection.getPoint().x,0.01);
		assertEquals(-1.21, intersection.getPoint().y,0.01);
		assertEquals(0.11, intersection.getPoint().z,0.01);
	
		assertEquals(2.71, intersection.getDistance(),0.01);
	}
	
	@Test
	public void testSphereNoRayIntersection() {
		Sphere sphere = new Sphere(new Point3D(1,-1,2), 2, 1, 1, 1, 1, 1, 1, 1);
		RayIntersection intersection = sphere.findClosestRayIntersection(new Ray(new Point3D(5,5,5),new Point3D(1,-2,1)));
		assertNull(intersection);
	}
	
}
