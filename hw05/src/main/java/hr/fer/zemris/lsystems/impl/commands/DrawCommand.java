package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which represents command for moving turtle by given step and drawing
 * line of that turtle step.
 * 
 * @author Filip Hustić
 *
 */
public class DrawCommand implements Command {

	private double step;

	/**
	 * Constructor which takes in step by which turtle will be going forwards.
	 * 
	 * @param step by which turtle will be going forwards.
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();
		double length = turtleState.distanceUnit * step;
		Vector2D start = turtleState.currentPosition;
		Vector2D stepVector= turtleState.direction.scaled(length);
		Vector2D end = start.translated(stepVector); 
		
		painter.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), turtleState.color, 1f);
		turtleState.currentPosition=end;
	}

}
