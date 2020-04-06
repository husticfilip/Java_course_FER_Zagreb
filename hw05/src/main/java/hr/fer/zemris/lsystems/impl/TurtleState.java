package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class which represents turtle state in coordiante system.
 * 
 * @author Filip Hustić
 *
 */
public class TurtleState {

	/**
	 * Vector which represents current position of turtle.
	 */
	public Vector2D currentPosition;
	/**
	 * Normalized vector which represents direction in which turtle is facing.
	 */
	public Vector2D direction;
	/**
	 * Color the turtle is leaving behind.
	 */
	public Color color;
	/**
	 * Distance which turtle overcome in one step;
	 */
	public double distanceUnit;

	/**
	 * Constructor which takes in position of turtle and direction of turtle in form
	 * of vector, color the turtle is leaving behind and distance the turtle
	 * overcomes in one step.
	 * 
	 * @param currentPosition vector which holds coordinates of turtles position.
	 * @param direction       vector which holds turtle direction.
	 * @param color           the turtle is leaving behind.
	 * @param step            which turtle overcome in one step;
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double distanceUnit) {
		super();
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.distanceUnit= distanceUnit;
	}

	/**
	 * Method copies state of the turtle in instance of new TurtleState.
	 * 
	 * @return copy of @this TurtleState.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), color, distanceUnit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((currentPosition == null) ? 0 : currentPosition.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distanceUnit);
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
		TurtleState other = (TurtleState) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (currentPosition == null) {
			if (other.currentPosition != null)
				return false;
		} else if (!currentPosition.equals(other.currentPosition))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (Double.doubleToLongBits(distanceUnit) != Double.doubleToLongBits(other.distanceUnit))
			return false;
		return true;
	}
	
	

}
