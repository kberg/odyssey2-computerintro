package org.brainkandy.oci.math;

/**
 * BCD (Binary Coded Decimal) utility methods.
 * 
 * I'd love to see this class go away, since it's likely that these need to live
 * inside IOperations, Operations or Computer.
 */
public class BCD {
	private static void validate(int number) {
		if (number < 0 || number > 99) {
			throw new IllegalArgumentException("Invalid number for BCD" + number);
		}
	}

	/**
	 * Returns a two-byte array representing the supplied number. The first
	 * byte represents the high base-10 digit, and the second byte represents
	 * the low base-10 digit.
	 */
	public static byte[] split(int number) {
		validate(number);
		return new byte[] { 
			(byte) (number / 10), (byte) (number % 10)
		};
	}
}
