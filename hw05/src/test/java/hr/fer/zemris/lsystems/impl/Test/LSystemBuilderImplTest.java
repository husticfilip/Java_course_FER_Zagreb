package hr.fer.zemris.lsystems.impl.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.math.Vector2D;

public class LSystemBuilderImplTest {

	@Test
	public void testProductionLevel0() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerProduction('F', "  F+  F-  -F+F   ").setAxiom("F");

		assertEquals("F", system.build().generate(0));
	}

	@Test
	public void testProductionLevel1() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerProduction('F', "  F+  F-  -F+F   ").setAxiom("F");

		assertEquals("F+F--F+F", system.build().generate(1));
	}

	@Test
	public void testProductionLevel2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerProduction('F', "F+F--F+F").setAxiom("F");

		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.build().generate(2));
	}

	@Test
	public void testProductionWithComplexAxiomLevel0() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerProduction('F', "  F+  F-  -F+F   ").setAxiom("F+F--FFF");

		assertEquals("F+F--FFF", system.build().generate(0));
	}

	@Test
	public void testRegisterNumericCommandsAndColor() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerCommand('D', "   draw   2").registerCommand('K', "skip 1  ").registerCommand('S', "scale 0.2")
				.registerCommand('R', "rotate 0.2").registerCommand('C', "color 000000");

		Dictionary<Character, String> systemsDic = system.getCommands();
		assertEquals("draw 2", systemsDic.get('D'));
		assertEquals("skip 1", systemsDic.get('K'));
		assertEquals("scale 0.2", systemsDic.get('S'));
		assertEquals("rotate 0.2", systemsDic.get('R'));
		assertEquals("color 000000", systemsDic.get('C'));
	}

	@Test
	public void testRegisterPushPop() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		system.registerCommand('P', "push").registerCommand('O', "pop");

		Dictionary<Character, String> systemsDic = system.getCommands();
		assertEquals("push", systemsDic.get('P'));
		assertEquals("pop", systemsDic.get('O'));
	}

	@Test
	public void testHexColor() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		Dictionary<Character, String> systemsDic = system.getCommands();
		system.registerCommand('C', "color ff0000");
		assertEquals("color ff0000", systemsDic.get('C'));
	}

	@Test
	public void testInvalidDoubleValue() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('D', "draw 12a");
		});
	}

	@Test
	public void testInvalidArgsOfargs() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('D', "draw 12a");
		});
	}

	@Test
	public void testInvalidNumOfargs() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('D', "draw 12 2");
		});
	}

	@Test
	public void testInvalidNumOfargs2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('D', "");
		});
	}

	@Test
	public void testInvalidNumOfargs3() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('P', "push 1");
		});
	}

	@Test
	public void testInvalidNumOfCommand() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		assertThrows(IllegalArgumentException.class, () -> {
			system.registerCommand('P', "mul 1");
		});
	}

	@Test
	public void testSettingAngleOf0() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(0);
		assertEquals(0, system.getAngle());
	}

	@Test
	public void testSettingAngleOf90() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(90);
		assertEquals(Math.PI / 2, system.getAngle());
	}

	@Test
	public void testSettingAngleOf180() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(180);
		assertEquals(Math.PI, system.getAngle());
	}

	@Test
	public void testSettingAngleOf270() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(270);
		assertEquals(Math.PI * 3 / 2, system.getAngle());
	}

	@Test
	public void testSettingAngleOf360() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(360);
		assertEquals(0, system.getAngle());
	}

	@Test
	public void testSettingAngleOf45() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(45);
		assertEquals(Math.PI / 4, system.getAngle(), 0.001);
	}

	@Test
	public void testSettingAngleOf135() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(135);
		assertEquals(Math.PI * 3 / 4, system.getAngle(), 0.001);
	}

	@Test
	public void testSettingAngleOfMinus45() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();

		system.setAngle(-45);
		assertEquals(-Math.PI / 4, system.getAngle(), 0.001);
	}

	@Test
	public void testPushCommand() {
		Context ctx = new Context();
		Painter painter = (a, b, c, d, e, f) -> {
		};
		TurtleState state = new TurtleState(new Vector2D(0, 0), new Vector2D(0, 1), Color.BLACK, 1);
		ctx.pushState(state);

		new PushCommand().execute(ctx, painter);

		assertEquals(state, ctx.getCurrentState());
		ctx.popState();
		assertEquals(state, ctx.getCurrentState());
	}

	@Test
	public void testScaleCommand() {
		Context ctx = new Context();
		Painter painter = (a, b, c, d, e, f) -> {
		};
		TurtleState state = new TurtleState(new Vector2D(0, 0), new Vector2D(0, 1), Color.BLACK, 1);
		ctx.pushState(state);

		new ScaleCommand(0.2).execute(ctx, painter);

		assertEquals(0.2, ctx.getCurrentState().distanceUnit);
	}

	@Test
	public void testOriginViaText() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] { "origin 0.05 4" };
		system.configureFromText(text);

		assertEquals(new Vector2D(0.05, 4), system.getOrigin());
	}

	@Test
	public void testIvalidOriginText() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] { "origin 4" };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testIvalidOriginText2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] { "origin 4 asv" };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testAngleViaText() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {"", "   angle               0.05" ,""};
		system.configureFromText(text);

		assertEquals(0.05, system.getAngle());
	}
	
	@Test
	public void testIvalidAngle() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {" angle  12 12" };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testIvalidAngle2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {" angle  a " };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testUnitViaText() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {"", "   unitLength               0.05" ,""};
		system.configureFromText(text);

		assertEquals(0.05, system.getUnitLength());
	}
	
	@Test
	public void testIvalidUnit1() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {" unitLength  " };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testUnit2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {" unitLength  a " };

		assertThrows(IllegalArgumentException.class, () -> {
			system.configureFromText(text);
		});
	}
	
	@Test
	public void testUnitLengthViaText() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {"", "   unitLengthDegreeScaler               0.05" ,""};
		system.configureFromText(text);

		assertEquals(0.05, system.getUnitLengthDegreeScaler());
	}
	
	@Test
	public void testUnitLengthViaTextFractions() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {"", "   unitLengthDegreeScaler    1    /       3" ,""};
		system.configureFromText(text);

		assertEquals((double)1/3, system.getUnitLengthDegreeScaler(),0.001);
	}
	@Test
	public void testUnitLengthViaTextFractions2() {
		LSystemBuilderImpl system = new LSystemBuilderImpl();
		String[] text = new String[] {"", "   unitLengthDegreeScaler    1/3" ,""};
		system.configureFromText(text);

		assertEquals((double)1/3, system.getUnitLengthDegreeScaler(),0.001);
	}

}
