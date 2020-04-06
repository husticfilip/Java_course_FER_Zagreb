package hr.fer.zemris.java.hw07.demo2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class PrimesCollectiotTest {
	
	@Test
	public void testPrimes() {
		PrimesCollection coll = new PrimesCollection(7);
		Iterator<Integer> it= coll.iterator();
		
		assertTrue(it.hasNext());
		assertEquals(2, it.next());
		assertEquals(3, it.next());
		assertEquals(5, it.next());
		assertEquals(7, it.next());
		assertTrue(it.hasNext());
		assertEquals(11, it.next());
		assertEquals(13, it.next());
		assertEquals(17, it.next());
		
		assertThrows(IndexOutOfBoundsException.class, ()->{
			it.next();
		});
		assertFalse(it.hasNext());
	}

}
