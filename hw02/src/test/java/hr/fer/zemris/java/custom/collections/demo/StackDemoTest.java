package hr.fer.zemris.java.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.demo.StackDemo;

public class StackDemoTest {
	
	

	@Test
	public void validCalculations() {
		ObjectStack stack;
		stack=new ObjectStack();
		
		StackDemo.calculation("8  2   /", stack );
		assertEquals(4, stack.pop());

		StackDemo.calculation("-1 8 2 / +", stack );
		assertEquals(3, stack.pop());

		StackDemo.calculation("8 -2 / -1 *", stack );
		assertEquals(4, stack.pop());
	
		StackDemo.calculation("10 2 8 * + 3 -", stack );
		assertEquals(23, stack.pop());
	
		StackDemo.calculation("10 2 + 8 - 3 +", stack );
		assertEquals(7, stack.pop());
	
		StackDemo.calculation("    10   8  %   ", stack );
		assertEquals(2, stack.pop());
	
		StackDemo.calculation("7 3 / 3 5 + * 2 -", stack );
		assertEquals(14, stack.pop());
	}
	
	@Test
	public void devideWithZero() {
		ObjectStack stack;
		stack=new ObjectStack();
		
		StackDemo.calculation("8  0   /", stack );
		StackDemo.calculation("8  0   %", stack );

	}
	
	@Test
	public void someWrongInputs() {
		ObjectStack stack;
		stack=new ObjectStack();
		
		StackDemo.calculation("8  2  4 /", stack );
		assertEquals(true, stack.size()!=1);
		
		stack.clear();
		StackDemo.calculation("8  2  4 / + -", stack );
		
		StackDemo.calculation("8  2  4 - .", stack );
	}
}
