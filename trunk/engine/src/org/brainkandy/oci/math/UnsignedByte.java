package org.brainkandy.oci.math;

public final class UnsignedByte {
	public static final UnsignedByte ZERO = new UnsignedByte(0);
	private static final UnsignedByte ONE = new UnsignedByte(1);

	public static UnsignedByte get(int x) {
		return new UnsignedByte(x);
	}

	private final int first;
	private final int second;
	private final int value;

	private UnsignedByte(int x) {
		validate(x);
		this.first = x / 16;
		this.second = x % 16;
		this.value = x;
	}

	private static void validate(int x) {
		 if (x < 0 || x > 255) {
		   throw new IllegalArgumentException("Invalid value: " + x);
		 }
	}

	/**
	 * Returns an UnsignedByte whose value is this + val.
	 * 
	 * TODO: deal with overflow.
	 */
	public UnsignedByte bcdAdd(UnsignedByte val) {
		int bcdSum = bcdGet() + val.bcdGet();
		int sum = denormalize(bcdSum);
		return UnsignedByte.get(sum);
	}

	/**
	 * Returns an UnsignedByte whose value is this - val.
	 */
	public UnsignedByte bcdSubtract(UnsignedByte val) {
		int difference = denormalize(bcdGet() - val.bcdGet());
		return UnsignedByte.get(difference);
	}

	public UnsignedByte bcdIncrement() {
		return bcdAdd(UnsignedByte.ONE);
	}

	private int denormalize(int i) {
		if (i >= 100) {
			// Could be that it should be i % 100 across the board.
			return 0;
		}
		int firstDigit = i / 10;
		int secondDigit = i % 10;
		return firstDigit * 16 + secondDigit;
	}

	public UnsignedByte bcdDecrement() {
		return bcdSubtract(UnsignedByte.ONE);
	}

	public int toInteger() {
		return value;
	}

	public int bcdGet() {
		return ((first * 10) + second) % 100;
	}

	public byte toByte() {
		return (byte) value;
	}

	public UnsignedByte firstDigit() {
		return get(first);
	}

	public UnsignedByte secondDigit() {
		return get(second);
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UnsignedByte)) {
			return false;
		}
		return value == ((UnsignedByte) obj).value;
	}

	@Override
	public String toString() {
		String string = Integer.toString(value, 16);
		if (string.length() == 1) {
			string = "0" + string;
		}
		return string;
	}

	public static UnsignedByte get(String string) {
		int sixteens = Character.digit(string.charAt(0), 16) << 4;
		int ones = Character.digit(string.charAt(1), 16);
		return UnsignedByte.get(sixteens + ones);
	}
}
