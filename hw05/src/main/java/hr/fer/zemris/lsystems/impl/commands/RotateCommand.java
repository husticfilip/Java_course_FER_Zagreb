package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for rotating direction of the active turtle
 * state by given angle through constructor.
 * 
 * @author Filip Hustić
 *
 */
public class RotateCommand implements Command {

	private double angle;

	/**
	 * Constructor which takes in angle by which turtle will be turned.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().direction.rotate(Math.toRadians(angle));
	}
	
	private double angleToRadian(double angle) {

		if (Math.abs(angle - 0) < 1e-4 || Math.abs(angle - 360) < 1e-4)
			angle = 0;
		else if (Math.abs(angle - 90) < 1e-4)
			angle = Math.PI / 2;
		else if (Math.abs(angle - 180) < 1e-4)
			angle = Math.PI;
		else if (Math.abs(angle - 270) < 1e-4)
			angle = Math.PI * 3 / 2;
		else
			angle = angle * 2 * Math.PI / 360;
		return angle;
	}

}
