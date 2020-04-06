package hr.fer.zemris.java.hw06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.crypto.Util;

public class UtilTest {
	
	
	@Test
	public void testNullStringHexToByte() {
		assertThrows(NullPointerException.class, ()->{
			Util.hextobyte(null);
		});
	}
	
	@Test
	public void testOddNumberStringHexToByte() {
		assertThrows(IllegalArgumentException.class, ()->{
			Util.hextobyte("abc");
		});
	}
	
	@Test
	public void testValidHexStringHexToByte() {
		byte[] byteArray = Util.hextobyte("01aE22");
		assertEquals(1, byteArray[0]);
		assertEquals(-82, byteArray[1]);
		assertEquals(34, byteArray[2]);
	}
	
	@Test
	public void testEmptyStringStringHexToByte() {
		byte[] byteArray = Util.hextobyte("");
		assertEquals(0, byteArray.length);
	}
	
	@Test
	public void testAllZeroStringHexToByte() {
		byte[] byteArray = Util.hextobyte("000000");
		assertEquals(0, byteArray[0]);
		assertEquals(0, byteArray[1]);
		assertEquals(0, byteArray[2]);
	}
	
	@Test
	public void testAllFStringHexToByte() {
		byte[] byteArray = Util.hextobyte("ffFfFF");
		assertEquals(-1, byteArray[0]);
		assertEquals(-1, byteArray[1]);
		assertEquals(-1, byteArray[2]);
	}
	
	@Test
	public void testEmptyArrayStringByteToHex() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
	@Test
	public void testByteToHex() {
		byte[] byteArray = {1, -82 ,34};
		assertEquals("01ae22", Util.bytetohex(byteArray));
	}
	
	@Test
	public void testByteToHex2() {
		byte[] byteArray = {-1, -1 ,-1};
		assertEquals("ffffff", Util.bytetohex(byteArray));
	}
	
	@Test
	public void testByteToHex3() {
		byte[] byteArray = {0, 0 ,0};
		assertEquals("000000", Util.bytetohex(byteArray));
	}

}
