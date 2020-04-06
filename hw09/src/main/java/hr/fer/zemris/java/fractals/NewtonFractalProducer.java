package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonFractalProducer implements IFractalProducer {

	/**
	 * Roots(zeros) of complex polynomial.
	 */
	private ComplexRootedPolynomial roots;

	/**
	 * Complex polynomial.
	 */
	private ComplexPolynomial polynomial;
	/**
	 * First derivation of complex polynoimal.
	 */
	private ComplexPolynomial derived;
	/**
	 * Number of sections which represent one individual job.
	 */
	private final int NUMBER_OF_LANES = 8 * Runtime.getRuntime().availableProcessors();
	/**
	 * Treshold used to determine to stop or not to stop iterating through
	 * polynomial approximation. If |zn+1-zn|<treshold iteration is stopped.
	 */
	private final double CONVERGENCE_TRESHOLD = 1E-3;

	/**
	 * Treshold used to determine minimum distance between complex number and root
	 * of polynomial.
	 */
	private final double ROOT_DISTANCE_TRESHOLD = 0.002;
	/**
	 * Number of iterations in polynomial approximation.
	 */
	private final int NUMER_OR_ITERATIONS = 16 * 16 * 16;

	private ExecutorService pool;

	/**
	 * Constructor which takes in roots of polynomial and calculates polynomial of
	 * those roots and also calculates derivation of that polynomial.
	 * 
	 * @param roots
	 */
	public NewtonFractalProducer(ComplexRootedPolynomial roots) {
		super();
		this.roots = roots;
		this.polynomial = roots.toComplexPolynom();
		this.derived = polynomial.derive();
		this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
			Thread worker = new Thread(r);
			worker.setDaemon(true);
			return worker;
		});
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requstNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Počinjem sa izračunom.");
		int rowsPerLine = height / NUMBER_OF_LANES;
		short data[] = new short[height * width];

		List<Future<Void>> jobs = new ArrayList<>();

		for (int i = 0; i < NUMBER_OF_LANES; ++i) {
			int yMin = i * rowsPerLine;
			int yMax = (i + 1) * rowsPerLine;
			if (i == NUMBER_OF_LANES - 1) {
				yMax = height - 1;
			}
			CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
					cancel);
			jobs.add(pool.submit(job));
		}

		for (Future<Void> job : jobs) {

			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		System.out.println("Izračun završen. Šaljem podatke GIU-u!");
		observer.acceptResult(data, (short) (polynomial.order() + 1), requstNo);

	}

	/**
	 * Class which represents one job which calculates polynomial approximations for
	 * one section(Line).
	 * <p>
	 * Each line is consiste of pixels. Each pixel is assigned to number of complex
	 * plane.
	 * <p>
	 * <p>
	 * For every pixel approximation of nearest polynomial root is calculated and
	 * result is stored in data[] by assigning index to the closest root form number
	 * assigned to pixel in data[pixel].
	 * <p>
	 * 
	 * @author Filip Hustić
	 *
	 */
	private class CalculationJob implements Callable<Void> {
		/**
		 * Minimum real value of complex plane.
		 */
		private double reMin;
		/**
		 * Maximum real value of complex plane.
		 */
		private double reMax;
		/**
		 * Minimum imaginary value of complex plane.
		 */
		private double imMin;
		/**
		 * Maximum imaginary value of complex plane.
		 */
		private double imMax;
		/**
		 * Width of raster plane.
		 */
		private int width;
		/**
		 * Height of raster plane.
		 */
		private int height;
		/**
		 * Start raster y for line assigned to this job.
		 */
		private int yMin;
		/**
		 * End raster y for line assigned to this job.
		 */
		private int yMax;
		/**
		 * Array which stores root index for every pixel.
		 */
		private short[] data;

		/**
		 * Flag which signals this job to stop calculation.
		 */
		private AtomicBoolean cancel;

		/**
		 * Constructor.
		 * 
		 * @param reMin  Minimum real value of complex plane.
		 * @param reMax  Maximum real value of complex plane.
		 * @param imMin  Minimum imaginary value of complex plane.
		 * @param imMax  Maximum imaginary value of complex plane.
		 * @param width  Width of raster plane.
		 * @param height Height of raster plane.
		 * @param yMin   Start raster y for line assigned to this job.
		 * @param yMax   End raster y for line assigned to this job.
		 * @param data   Array which stores root index for every pixel.
		 */
		public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() throws Exception {

			for (int y = yMin; y < yMax; ++y) {
				for (int x = 0; x < width; ++x) {
					if (cancel.get()) {
						return null;
					}

					Complex number = new Complex(getRealPart(x, reMin, reMax, width),
							getImaginaryPart(y, imMin, imMax, height));

					int closest = roots.indexOfClosestRootFor(generateNumber(number), ROOT_DISTANCE_TRESHOLD);
					data[y * width + x] = (short) (closest+1);
				}
			}
			return null;
		}

		/**
		 * Method which generates approximated number which goes towards nearest local
		 * root.
		 * <p>
		 * Number is approximated by formula : <br>
		 * zn+1 = zn - f(zn)/f'(zn)<br>
		 * <br>
		 * Iterations are done until maximum number of iterations has been reached or
		 * until |zn+1-zn|<CONVERGENCE_TRESHOLD
		 * <p>
		 * 
		 * @param complex number for which approximation will be done.
		 * @return approximation of number.
		 */
		private Complex generateNumber(Complex complex) {
			Complex znold = complex.copyOf();
			Complex zn = null;

			for (int i = 0; i < NUMER_OR_ITERATIONS; ++i) {
				Complex numerator = polynomial.apply(znold);
				Complex denominator = derived.apply(znold);
				Complex quotient = numerator.divide(denominator);
				zn = znold.sub(quotient);
				if (Math.abs(zn.sub(znold).module()) < CONVERGENCE_TRESHOLD) {
					break;
				}
				znold = zn;
			}

			return znold;
		}

		/**
		 * Method converts raster coordinate x to real part of complex number.
		 * 
		 * @param x     raster coordinate.
		 * @param reMin minimum real value of complex plane.
		 * @param reMax maximum real value of complex plane.
		 * @param width of raster.
		 * @return real part of number.
		 */
		private double getRealPart(int x, double reMin, double reMax, int width) {
			return (double) x / (width - 1) * (reMax - reMin) + reMin;
		}

		/**
		 * Method converts raster coordinate y to imaginary part of complex plane.
		 * 
		 * @param y      raster coordinate.
		 * @param imMin  minimum imaginary value of complex plane.
		 * @param imMax  reMax maximum real value of complex plane.
		 * @param height of raster.
		 * @return imaginary part of number.
		 */
		private double getImaginaryPart(int y, double imMin, double imMax, int height) {
			return (double) (height - 1 - y) / (height - 1) * (imMax - imMin) + imMin;
		}
	}

}