package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for scaling the step of the turtle, turtle is moving
 * forwards by its step*factor but step of the turtle does not change.
 * @author Filip Hustić
 *
 */
public class ScaleCommand implements Command {
	
	private double scaler;

	/**
	 * Constructor which takes in scaler by which step of the 
	 * turtle will be scaled while moving.
	 * @param scaler of the turtle step.
	 */
	public ScaleCommand(double scaler) {
		super();
		this.scaler = scaler;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().distanceUnit*=scaler;
	}
	
	

}
