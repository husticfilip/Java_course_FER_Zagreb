package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for changing color of the line.
 * @author Filip Hustić
 *
 */
public class ColorCommand implements Command{

	private Color color;

	/**
	 * Constructor which takes in color of the line which will be left behind
	 * the turtle
	 * @param color of lines.
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().color=color;
	}
	
	
	
}
