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
		// first and second can be functions.
		this.first = x / 16;
		this.second = x % 16;
		this.value = x;
	}

	private static void validate(int x) {
//		if (x < 0 || x > 255) {
//			throw new IllegalArgumentException("Invalid value: " + x);
//		}
	}

	/**
	 * Returns an UnsignedByte whose value is this + val.
	 *
	 * TODO: deal with overflow.
	 */
	public UnsignedByte bcdAdd(UnsignedByte val) {
		int bcdSum = toBcdNumber() + val.toBcdNumber();
		int sum = denormalize(bcdSum);
		return UnsignedByte.get(sum);
	}

	/**
	 * Returns an UnsignedByte whose value is this - val.
	 */
	public UnsignedByte bcdSubtract(UnsignedByte val) {
		int difference = denormalize(toBcdNumber() - val.toBcdNumber());
		return UnsignedByte.get(difference);
	}

	public UnsignedByte bcdIncrement() {
		return bcdAdd(UnsignedByte.ONE);
	}

	private int denormalize(int i) {
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

	public int toBcdNumber() {
		return (first * 10) + second;
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

	public String toHexString() {
		String string = Integer.toString(value, 16);
		if (string.length() == 1) {
			string = "0" + string;
		}
		return string;
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
		return toHexString();
	}
}
