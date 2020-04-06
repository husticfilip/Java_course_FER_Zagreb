package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which represents command for adding one state form the top of the stack.
 * @author Filip Hustić
 *
 */
public class PushCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copyOfTopState = ctx.getCurrentState().copy();
		ctx.pushState(copyOfTopState);
	}

}
