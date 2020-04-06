package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which through its methods sets up initial parameters for L system.
 * 
 * @author Filip Hustić
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Dictionary which holds commands for L system.
	 */
	private Dictionary<Character, String> commands;
	/**
	 * Dictionary which holds productions for L system.
	 */
	private Dictionary<Character, String> productions;

	/**
	 * Length of distance turtle will overcome with step of size 1. Initial value of
	 * unitLength is 0.1.
	 */
	private double unitLength = 0.1;
	/**
	 * Scaler for drawing in n.th level. Unit length of n-th level is calculated by:
	 * unitLength = unitLength(initial) * unitLengtDegreeScal^n;
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Start coordinates of turtle. Initial start coordinates are at (0,0).
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Angle in which direction turtle is facing. Initial angle is 0.
	 */
	private double angle = 0;
	/**
	 * Axiom of L system.
	 */
	private String axiom = "";

	/**
	 * Default constructor which initializes commands and production dictionary.
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary<Character, String>();
		productions = new Dictionary<Character, String>();
	}

	@Override
	public LSystem build() {
		return new DrawSystem();
	}

	@Override
	public LSystemBuilder configureFromText(String[] text) {

		for (String line : text) {

			if (!line.isEmpty()) {
				// for fractions(eq 1/3 -> 1 / 3)
				line = line.replaceAll("/", " / ");
				line = line.trim().replaceAll("\\s+", " ");;
				String[] splited = line.split(" ");
				if (splited[0].equals("origin"))
					addOrigin(splited);
				else if (splited[0].equals("angle"))
					addAngle(splited);
				else if (splited[0].equals("unitLength"))
					addUnitLength(splited);
				else if (splited[0].equals("unitLengthDegreeScaler"))
					addUnitLengthDegreeScaler(splited);
				else if (splited[0].equals("command") && splited[1].length() == 1)
					registerCommand(splited[1].charAt(0),
							line.substring(splited[0].length() + splited[1].length() + 2));
				else if (splited[0].equals("production") && splited[1].length() == 1)
					registerProduction(splited[1].charAt(0),
							line.substring(splited[0].length() + splited[1].length() + 2));
				else if (splited[0].equals("axiom"))
					axiom = line.substring(splited[0].length()).replaceAll("\\s+", "");
				else
					throw new IllegalArgumentException("Given configuration was not valid.");
			}
		}

		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char key, String value) {
		if (value == null)
			throw new NullPointerException("Value was null");

		value = value.trim().replaceAll("\\s+", " ");
		String[] splitted = value.split(" ");

		if (splitted[0].equals("draw") || splitted[0].equals("skip") || splitted[0].equals("scale")
				|| splitted[0].equals("rotate")) {
			tryIfValidArgumentNumber(splitted);
			commands.put(key, value);

		} else if (splitted[0].equals("color")) {
			tryIfValidArgumentColor(splitted);
			commands.put(key, value);
		} else if (splitted[0].equals("push") || splitted[0].equals("pop")) {

			if (splitted.length != 1)
				throw new IllegalArgumentException("Invalid number of values in command");
			else
				commands.put(key, value);
		} else {
			throw new IllegalArgumentException("Command name vas not valid.");
		}

		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char key, String value) {
		if (value == null)
			throw new NullPointerException("Value was null");

		productions.put(key, value.replaceAll("\\s+", " "));
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {

		if (Math.abs(angle - 0) < 1e-4 || Math.abs(angle - 360) < 1e-4)
			this.angle = 0;
		else if (Math.abs(angle - 90) < 1e-4)
			this.angle = Math.PI / 2;
		else if (Math.abs(angle - 180) < 1e-4)
			this.angle = Math.PI;
		else if (Math.abs(angle - 270) < 1e-4)
			this.angle = Math.PI * 3 / 2;
		else
			this.angle = angle * 2 * Math.PI / 360;

		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom.replaceAll("\\s+", " ");;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		this.unitLengthDegreeScaler = scaler;
		return this;
	}

	/**
	 * Helper method which tests if there is valid number of arguments for draw,
	 * skip, scale and rotate command. And if second argument is double.
	 * 
	 * @param splitted string split into tokens.
	 */
	private void tryIfValidArgumentNumber(String[] splitted) {
		if (splitted.length != 2)
			throw new IllegalArgumentException("Invalid number of values in command");

		// testing if second argument is double
		try {
			Double.parseDouble(splitted[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					"Wrong argument for command type draw,skip,scale or rotate. Double is expected.");
		}
	}

	/**
	 * Helper method which tests if there is valid number of arguments for color
	 * command. And if second argument is integer.
	 * 
	 * @param splitted string split into tokens.
	 */
	private void tryIfValidArgumentColor(String[] splitted) {
		if (splitted.length != 2)
			throw new IllegalArgumentException("Invalid number of values in command");

		// testing if second argument is int
		try {
			Integer.parseInt(splitted[1], 16);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					"Wrong argument for command type draw,skip,scale or rotate. Double is expected.");
		}
	}

	/**
	 * Helper method which sets unitLengthDegreeScaler.
	 * <p>
	 * Method tests if text was in right format, if not it throws exception.
	 * <p>
	 * 
	 * @param splited holds value to be set.
	 * @throws IllegalArgumentException if there was illegal number of arguments in
	 *                                  line or if value for unitLengthDegreeScaler
	 *                                  was not double.
	 */
	private void addUnitLengthDegreeScaler(String[] splited) {
		if (splited.length != 2 && splited.length != 4)
			throw new IllegalArgumentException("Invalid number of argumenrs");

		if (splited.length == 2) {
			try {
				unitLengthDegreeScaler = Double.parseDouble(splited[1]);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Arguments for unitLengthDegreeScaler must be double numbers");
			}
		} else if (splited.length == 4 && splited[2].equals("/")) {
			try {
				double a = Double.parseDouble(splited[1]);
				double b = Double.parseDouble(splited[3]);
				unitLengthDegreeScaler = a / b;
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Arguments for unitLengthDegreeScaler must be double numbers");
			}
		} else {
			throw new IllegalArgumentException(
					"Arguments for unitLengthDegreeScaler must be double numbers and between then can come //.");
		}

	}

	/**
	 * Helper method which sets unitLength.
	 * <p>
	 * Method tests if text was in right format, if not it throws exception.
	 * <p>
	 * 
	 * @param splited holds value to be set.
	 * @throws IllegalArgumentException if there was illegal number of arguments in
	 *                                  line or if value for unitLength was not
	 *                                  double.
	 */
	private void addUnitLength(String[] splited) {
		if (splited.length != 2)
			throw new IllegalArgumentException("Given number of arguments of unitLength is not right.");

		try {
			unitLength = Double.parseDouble(splited[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Arguments for unitLength must be double numbers");
		}
	}

	/**
	 * Helper method which sets up initial angle of LSystem. Method tests if text
	 * was in right format, if not it throws exception.
	 * <p>
	 * 
	 * @param splited holds value to be set.
	 * @throws IllegalArgumentException if there was illegal number of arguments in
	 *                                  line or if value for angle was not double.
	 */
	private void addAngle(String[] splited) {
		if (splited.length != 2)
			throw new IllegalArgumentException("Given number of arguments of angle is not right.");

		try {
			angle = Double.parseDouble(splited[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Arguments for angle must be double numbers");
		}
	}

	/**
	 * Helper method which sets up initial position of LSystem.
	 * <p>
	 * Method tests if text was in right format, if not it throws exception.
	 * <p>
	 * 
	 * @param splited holds value to be set.
	 * @throws IllegalArgumentException if there was illegal number of arguments in
	 *                                  line or if values for origin coordinates were not double.
	 */
	private void addOrigin(String[] splited) {
		if (splited.length != 3)
			throw new IllegalArgumentException("Given number of arguments of origin is not right.");

		try {
			double x = Double.parseDouble(splited[1]);
			double y = Double.parseDouble(splited[2]);
			this.origin = new Vector2D(x, y);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Arguments for origin must be double numbers");
		}
	}

	public Dictionary<Character, String> getCommands() {
		return commands;
	}

	public Dictionary<Character, String> getProductions() {
		return productions;
	}

	public double getAngle() {
		return angle;
	}

	public double getUnitLength() {
		return unitLength;
	}

	public double getUnitLengthDegreeScaler() {
		return unitLengthDegreeScaler;
	}

	public Vector2D getOrigin() {
		return origin;
	}

	public String getAxiom() {
		return axiom;
	}

	/**
	 * Class which builds up LSystem with set configuration in LSystemBuilderImpl.
	 * 
	 * @author Filip Hustić
	 *
	 */
	private class DrawSystem implements LSystem {

		@Override
		public void draw(int level, Painter painter) {
			Context context = new Context();
			Vector2D direction = new Vector2D(1, 0).rotated(Math.toRadians(angle));
			context.pushState(new TurtleState(origin, direction, Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, level)));

			String generated = generate(level);
			char[] keys = generated.toCharArray();

			for (char key : keys) {
				String command = commands.get(key);
				if (command != null)
					processCommand(command.split(" "), context, painter);
			}

		}

		@Override
		public String generate(int level) {
			char[] keys = axiom.toCharArray();

			for (int k = 0; k < level; k++) {
				StringBuilder generated = new StringBuilder();

				for (char key : keys) {
					String toAppend = productions.get(key);
					if (toAppend != null)
						generated.append(toAppend);
					else
						generated.append(key);
				}

				keys = generated.toString().toCharArray();
			}

			return String.copyValueOf(keys).replaceAll("\\s+", "");
		}

		/**
		 * Helper method which takes in command and executes it.
		 * 
		 * @param command which will be executed.
		 * @param ctx     context of system.
		 * @param painter which will draw lines.
		 */
		private void processCommand(String[] command, Context ctx, Painter painter) {
			if (command[0].equals("draw"))
				new DrawCommand(Double.parseDouble(command[1])).execute(ctx, painter);
			else if (command[0].equals("skip"))
				new SkipCommand(Double.parseDouble(command[1])).execute(ctx, painter);
			else if (command[0].equals("scale"))
				new ScaleCommand(Double.parseDouble(command[1])).execute(ctx, painter);
			else if (command[0].equals("rotate"))
				new RotateCommand(Double.parseDouble(command[1])).execute(ctx, painter);
			else if (command[0].equals("push"))
				new PushCommand().execute(ctx, painter);
			else if (command[0].equals("pop"))
				new PopCommand().execute(ctx, painter);
			else
				new ColorCommand(new Color(Integer.parseInt(command[1], 16))).execute(ctx, painter);
		}

	}
}
