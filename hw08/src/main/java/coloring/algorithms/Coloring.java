package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Class represents strategy for which holds methods for space search algorithm
 * in picture.
 * <p>
 * Space search algorithm needs to have method for getting initial pixel(get),
 * method for testing if pixel is valid(test), method to return successors
 * pixels(apply) and method to light pixel with fill color.
 * 
 * @author Filip Hustić
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	/**
	 * Initial pixel.
	 */
	Pixel reference;
	/**
	 * Picture on which pixels will be lighted on.
	 */
	Picture picture;
	/**
	 * Color in which pixel will be lighted on.
	 */
	int fillColor;
	/**
	 * Color of initial pixel.
	 */
	int refColor;

	/**
	 * Constructor which takes initial pixel, picture and new color to be placed on
	 * pixels.
	 * 
	 * @param reference to initial pixel.
	 * @param picture
	 * @param fillColor new color to be placed on pixels.
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		Objects.requireNonNull(reference);
		Objects.requireNonNull(picture);

		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel pixel) {
		Objects.requireNonNull(pixel);
		if (refColor == fillColor) {
			return false;
		} else {
			return refColor == picture.getPixelColor(pixel.x, pixel.y);
		}
	}

	@Override
	public List<Pixel> apply(Pixel pixel) {
		Objects.requireNonNull(pixel);
		if (pixel.x < 0 || pixel.x >= picture.getWidth() || pixel.y < 0 || pixel.y >= picture.getHeight()) {
			throw new IndexOutOfBoundsException("Coordinate out of picture.");
		}

		List<Pixel> successors = new ArrayList<Pixel>();

		int x = pixel.x;
		int y = pixel.y;
		if (testPixel(x - 1, y))
			successors.add(new Pixel(x - 1, y));
		if (testPixel(x + 1, y))
			successors.add(new Pixel(x + 1, y));
		if (testPixel(x, y - 1))
			successors.add(new Pixel(x, y - 1));
		if (testPixel(x, y + 1))
			successors.add(new Pixel(x, y + 1));

		return successors;
	}

	/**
	 * Helper method which tests if coordinates are in the picture.
	 * 
	 * @param x coordinate of pixel.
	 * @param y coordinate of pixel.
	 * @return true if coordinates are in picture, false otherwise.
	 */
	private boolean testPixel(int x, int y) {
		if (x < 0 || x >= picture.getWidth() || y < 0 || y >= picture.getHeight())
			return false;
		else
			return true;
	}

	@Override
	public void accept(Pixel pixel) {
		Objects.requireNonNull(pixel);
		if (pixel.x < 0 || pixel.x >= picture.getWidth() || pixel.y < 0 || pixel.y >= picture.getHeight()) {
			throw new IndexOutOfBoundsException("Coordinate out of picture.");
		}

		picture.setPixelColor(pixel.x, pixel.y, fillColor);
	}

}
