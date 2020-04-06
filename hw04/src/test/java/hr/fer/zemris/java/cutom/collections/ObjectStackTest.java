package hr.fer.zemris.java.cutom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class ObjectStackTest {

	
	@Test
	public void testPustPopPeak() {
		ObjectStack<String> stack= new ObjectStack<>();
		stack.push("Boško");
		stack.push("Ivek");
		
		assertEquals("Ivek", stack.peak());
		
		assertEquals(stack.pop(), "Ivek");
		assertEquals(stack.pop(), "Boško");
	}
	
}
