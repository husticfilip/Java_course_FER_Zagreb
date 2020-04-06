package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ObjectStackTest {

	private ObjectStack stack;

	@Test
	public void testIsEmptyAndSize() {
		stack = new ObjectStack();
		assertEquals(true, stack.isEmpty());
		// size
		assertEquals(0, stack.size());

		stack.push("Boško");
		stack.push("Ivek");
		assertEquals(false, stack.isEmpty());
		// size
		assertEquals(2, stack.size());
	}

	@Test
	public void testPushingAndPoping() {
		stack = new ObjectStack();
		// null
		assertThrows(NullPointerException.class, () -> {
			stack.push(null);
		});
		stack.push("Ivek");
		stack.push("Boško");

		assertEquals("Boško", stack.pop());
		// size
		assertEquals(1, stack.size());

		assertEquals("Ivek", stack.pop());
		// size
		assertEquals(0, stack.size());

		assertThrows(EmptyStackException.class, () -> {
			stack.pop();
		});
		// size
		assertEquals(0, stack.size());
	}

	@Test
	public void testPeak() {
		stack = new ObjectStack();

		stack.push("Ivek");
		stack.push("Boško");

		assertEquals("Boško", stack.peak());
		assertEquals(2, stack.size());
		assertEquals("Boško", stack.pop());
		assertEquals("Ivek", stack.pop());

		assertThrows(EmptyStackException.class, () -> {
			stack.peak();
		});
	}

	@Test
	public void testCelar() {
		stack = new ObjectStack();

		stack.push("Ivek");
		stack.push("Boško");

		stack.clear();

		assertEquals(0, stack.size());
		assertThrows(EmptyStackException.class, () -> {
			stack.peak();
		});
	}
}
