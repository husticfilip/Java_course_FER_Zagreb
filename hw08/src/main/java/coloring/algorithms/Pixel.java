package coloring.algorithms;

/**
 * Class represents pixel in picture.
 * 
 * @author Filip Hustić
 *
 */
public class Pixel {

	/**
	 * X coordinate.
	 */
	public int x;
	/**
	 * Y coordinate.
	 */
	public int y;

	/**
	 * Default constructor which takes in coordinates of pixel.
	 * 
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
