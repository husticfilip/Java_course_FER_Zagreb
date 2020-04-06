package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class represents RayCaster of scene with objects in it. Class holds main
 * method which activates GUI and methods for calculating projections of rays.
 * 
 * @author Filip Hustić
 *
 */
public class RayCasterParallel {

	/**
	 * rgb value of object shined with ambient light with out any additional light
	 * sources.
	 */
	private static short AMBIENT_COLOR = 15;
	
	private static final int MAXIMUM_NUMBER_OF_LINES = 100;

	/**
	 * Entrance point of program.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}

	public static class CalculationJobs extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		
		private Point3D xAxis;
		private Point3D yAxis;
		private Point3D screenCorner;
		private Scene scene;
		private Point3D eye;
		private double horizontal;
		private double vertical;
		private int height;
		private int width;
		private int yStart;
		private int yEnd;
		private short[] red;
		private short[] green;
		private short[] blue;

		
		
		public CalculationJobs(Point3D xAxis, Point3D yAxis, Point3D screenCorner, Scene scene, Point3D eye,
				double horizontal, double vertical, int height, int width, int yStart, int yEnd, short[] red,
				short[] green, short[] blue) {
			super();
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.height = height;
			this.width = width;
			this.yStart = yStart;
			this.yEnd = yEnd;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		protected void compute() {
			
			if(yEnd-yStart<MAXIMUM_NUMBER_OF_LINES) {
				directCalulation();
			}else {
				CalculationJobs j1 = new CalculationJobs(xAxis, yAxis, screenCorner, scene, eye, horizontal, vertical, height, width, yStart, (yEnd-yStart)/2 + yStart, red, green, blue);
				CalculationJobs j2 = new CalculationJobs(xAxis, yAxis, screenCorner, scene, eye, horizontal, vertical, height, width, (yEnd-yStart)/2 +yStart, yEnd, red, green, blue);
				invokeAll(j1,j2);
			}
			return;
		}

		private void directCalulation() {

			int offset = yStart * width;
			short[] rgb = new short[3];
			for (int y = yStart; y < yEnd; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = calculateViewPlanePoint(screenCorner, xAxis, yAxis, horizontal, vertical,
							width, height, x, y);
					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}

	}

	/**
	 * Method returns IRayTracerProducer which projects new ambient with given
	 * objects and light sources.
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean abort) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				// z= Eye - View
				Point3D zAxis = view.sub(eye).normalize();
				Point3D normalizedViewUp = viewUp.normalize();
				double scalar = zAxis.scalarProduct(normalizedViewUp);
				// y= VuUpNorm - zAxis(zAxis*VuUpNorm)
				Point3D yAxis = normalizedViewUp.sub(zAxis.scalarMultiply(scalar)).normalize();
				// x= zAxis x yAxis
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				// top left corner in plane coordinate system
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculationJobs(xAxis, yAxis, screenCorner, scene, eye, horizontal, vertical, height, width, 0, height, red, green, blue));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Method which turns x and y coordinates of screen(raster) coordinate system
	 * into coordinates of projection plane coordinate system.
	 * <p>
	 * Raster coordinate system starts at top left corner with coordinate (0,0) and
	 * its values are rising down to (0,height) and right to (width,0).
	 * <p>
	 * <p>
	 * Projection plane coordinate system starts in the middle of raster coordinate
	 * system and behaves as ordinary coordinate system with x coordinates going
	 * [-width/2,width/2] and y coordinates going [-height/2,height/2]
	 * <p>
	 * 
	 * @param corner     top left corner of plane coordinate system.
	 * @param xAxis      norm vector of x axis in plane coordinate system.
	 * @param yAxis      norm vectror of y axis in plane coordinate system.
	 * @param horizontal width of plane coordinate system.
	 * @param vertical   height of plane coordinate system.
	 * @param width      of raster coordinate system.
	 * @param height     of raster coordinate system.
	 * @param x          in raster coordinate system.
	 * @param y          in raster coordinate system.
	 * @return point in projection plane coordinate system.
	 */
	private static Point3D calculateViewPlanePoint(Point3D corner, Point3D xAxis, Point3D yAxis, double horizontal,
			double vertical, int width, int height, int x, int y) {
		return corner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
				.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));
	}

	/**
	 * Method checks if given observer ray intersects with some object and if it
	 * does it finds the closest such object. <br>
	 * If object is found calculating effects of light sources is done via
	 * {@link determineColorFor}. If no object is found rgb components are set to 0.
	 * 
	 * @param scene       which holds objects, light sources and observer.
	 * @param observerRay ray which starts at observer and goes through projection
	 *                    plane for some point of projection plane.
	 * @param rgb         value of rgb which will be assigend to some point of
	 *                    projection plane.
	 */
	private static void tracer(Scene scene, Ray observerRay, short[] rgb) {
		List<GraphicalObject> objects = scene.getObjects();
		// while scene will be black
		if (objects.isEmpty()) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			return;
		}

		int indexOfClosest = 0;
		RayIntersection closestRayIntersection = objects.get(0).findClosestRayIntersection(observerRay);

		for (int i = 1; i < objects.size(); ++i) {
			RayIntersection intersection = objects.get(i).findClosestRayIntersection(observerRay);

			if (intersection != null) {

				if (closestRayIntersection == null) {
					closestRayIntersection = intersection;
					indexOfClosest = i;
				} else {
					if (intersection.getDistance() < closestRayIntersection.getDistance()) {
						closestRayIntersection = intersection;
						indexOfClosest = i;
					}
				}
			}
		}

		// there were no intersections
		if (closestRayIntersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(closestRayIntersection, objects.get(indexOfClosest), rgb, scene, observerRay);
		}
	}

	/**
	 * Method determines color for intersection of observer light and object.
	 * Intersection is questioned if some light source shines on it and if it does
	 * color is assigned to it by Phong reflection and difusion of light.
	 * <p>
	 * It is important that there are no objects between intersection and light
	 * source and that object itself does not block the light ray to shine on
	 * intersection.
	 * <p>
	 * <p>
	 * Also if no light shines on object, color value of object is set to predefined
	 * ambient color.
	 * <p>
	 * 
	 * @param intersection         of observer ray and object.
	 * @param objectOfIntersection object whit which observer ray intersects. l
	 * @param rgb                  will be assigned to value of intersection.
	 * @param scene                which holds objects, light sources and observer.
	 * @param observerRay          ray which comes from observers eye.
	 */
	private static void determineColorFor(RayIntersection intersection, GraphicalObject objectOfIntersection,
			short[] rgb, Scene scene, Ray observerRay) {
		rgb[0] = AMBIENT_COLOR;
		rgb[1] = AMBIENT_COLOR;
		rgb[2] = AMBIENT_COLOR;

		Point3D intersectionPoint = intersection.getPoint();
		for (LightSource light : scene.getLights()) {
			// ray from observers intersection to light source
			Ray pointToSourceRay = new Ray(intersectionPoint, light.getPoint().sub(intersectionPoint));
			// ray from light source to observers intersection
			Ray sourceToPointRay = new Ray(light.getPoint(), intersectionPoint.sub(light.getPoint()));

			// distance from observer's intersection and light source
			double lightToObjectIntersectionDistance = intersectionPoint.sub(light.getPoint()).norm();
			// closest intersection of objectOfIntersection and ray from light source to
			// observers intersection
			// object can block light ray from light source to get to its observers
			// intersection
			RayIntersection closestIntersection = objectOfIntersection.findClosestRayIntersection(sourceToPointRay);

			double closestIntersectionOfObjectDistance = closestIntersection == null ? lightToObjectIntersectionDistance
					: closestIntersection.getDistance();
			// if there is closer intersection to light source than observer intersection
			if (Math.abs(lightToObjectIntersectionDistance - closestIntersectionOfObjectDistance) > 0.001) {
				continue;
			}

			boolean intersects = false;
			for (GraphicalObject object : scene.getObjects()) {
				RayIntersection otherObjectIntersection = object.findClosestRayIntersection(pointToSourceRay);

				if (otherObjectIntersection != null
						&& otherObjectIntersection.getDistance() <= lightToObjectIntersectionDistance) {
					intersects = true;
					break;
				}

			}
			if (!intersects) {
				rgb[0] += difusionComponent(light.getR(), intersection.getKdr(), pointToSourceRay, intersection)
						+ reflectionComponent(light.getR(), intersection.getKrr(), pointToSourceRay, intersection,
								observerRay);
				rgb[1] += difusionComponent(light.getG(), intersection.getKdg(), pointToSourceRay, intersection)
						+ reflectionComponent(light.getG(), intersection.getKrg(), pointToSourceRay, intersection,
								observerRay);
				rgb[2] += difusionComponent(light.getB(), intersection.getKdb(), pointToSourceRay, intersection)
						+ reflectionComponent(light.getB(), intersection.getKrb(), pointToSourceRay, intersection,
								observerRay);
			}
		}
	}

	/**
	 * Method calculates vector reflected to normal.
	 * 
	 * @param vector from which reflected vector will be calculated.
	 * @param normal of reflection.
	 * @return reflected vector.
	 */
	private static Point3D getReflectedVectorNormalized(Point3D vector, Point3D normal) {
		double scalar = vector.scalarProduct(normal) * 2;
		return normal.scalarMultiply(scalar).sub(vector).normalize();
	}

	/**
	 * Method calculates Phongs light diffusion.
	 * 
	 * @param I            intensity of light.
	 * @param kd           diffuse coefficient.
	 * @param ligthSource  source of the light.
	 * @param intersection which is shined by the light.
	 * @return Phongs diffusion at given intersection shined by given lightSource.
	 */
	private static short difusionComponent(double I, double kd, Ray ligthSource, RayIntersection intersection) {
		return (short) (I * kd * ligthSource.direction.normalize().scalarProduct(intersection.getNormal()));
	}

	/**
	 * Method calculates Phongs reflection.
	 * 
	 * @param I            intensity of light.
	 * @param ks           reflection coefficient.
	 * @param lightSource  source of the light.
	 * @param intersection which is shined by the light.
	 * @param observerRay  ray which starts at observer eye and goes through
	 *                     intersection.
	 * @return Phongs reflection at given intersection by given lightSource in view
	 *         of observer.
	 */
	private static short reflectionComponent(double I, double ks, Ray lightSource, RayIntersection intersection,
			Ray observerRay) {
		// r*v
		double ortogonalObserverScalar = getReflectedVectorNormalized(lightSource.direction, intersection.getNormal())
				.scalarProduct(observerRay.direction.negate().normalize());
		return (short) (I * ks * Math.pow(ortogonalObserverScalar, intersection.getKrn()));
	}

}
