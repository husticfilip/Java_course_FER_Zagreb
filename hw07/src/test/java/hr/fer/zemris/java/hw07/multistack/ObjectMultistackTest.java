package hr.fer.zemris.java.hw07.multistack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ObjectMultistackTest {
	
	@Test
	public void testPush() {
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("Bosko", new ValueWrapper(12.12));
		assertEquals(12.12, stack.peek("Bosko").value);
		stack.push("Bosko", new ValueWrapper("008"));
		assertEquals("008", stack.peek("Bosko").value);
	}
	
	@Test
	public void testPeakNotInStack() {
		ObjectMultistack stack= new ObjectMultistack();
		assertThrows(EmptyStackException.class, ()->{
			stack.peek("Bosko");
		});
	}
	
	@Test
	public void testPop() {
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("Bosko", new ValueWrapper(1));
		stack.push("Bosko", new ValueWrapper(2));
		stack.push("Bosko", new ValueWrapper(3));

		assertEquals(3, stack.pop("Bosko").value);
		assertEquals(2, stack.peek("Bosko").value);
		assertEquals(2, stack.pop("Bosko").value);
		assertEquals(1, stack.pop("Bosko").value);
		
		assertThrows(EmptyStackException.class, ()->{
			stack.pop("Bosko");
		});
		assertThrows(EmptyStackException.class, ()->{
			stack.peek("Bosko");
		});
	}
	
	@Test
	public void testIsEmpty() {
		ObjectMultistack stack= new ObjectMultistack();
		assertTrue(stack.isEmpty("Bosko"));
		
		stack.push("Bosko", new ValueWrapper(1));
		assertFalse(stack.isEmpty("Bosko"));
		
		stack.pop("Bosko");
		assertTrue(stack.isEmpty("Bosko"));
	}

}
