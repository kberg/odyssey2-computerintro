package org.brainkandy.oci.math;

import junit.framework.TestCase;

public class UnsignedByteTest extends TestCase {
	public void testBcdIncrement() {
		UnsignedByte b = UnsignedByte.ZERO;
		for (int i = 1; i <= 99; i++) {
			UnsignedByte next = b.bcdIncrement();
			if (next.bcdGet() != i) {
				fail(String.format(
				"Expected increment %s [%s] to have bcd value %d, got %s [%s]",
				b, b.bcdGet(), i, next, next.bcdGet()));
			}
			b = next;
		}
		assertEquals(0, b.bcdIncrement().bcdGet());
	}

	public void testHexValues() {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		for(char first : hexChars) {
			for(char second : hexChars) {
				String expected = "" + first + second;
				UnsignedByte actual = UnsignedByte.get(expected);
				assertEquals(expected, actual.toString());
			}
		}
	}

	public void testToBcdNumber_Overflow() {
		assertEquals(34, UnsignedByte.get("2e").bcdGet());
		assertEquals(11, UnsignedByte.get("b1").bcdGet());
		assertEquals(20, UnsignedByte.get("ba").bcdGet());
	}
}
