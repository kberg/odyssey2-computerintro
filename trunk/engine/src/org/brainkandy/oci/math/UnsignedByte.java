package org.brainkandy.oci.math;

public final class UnsignedByte {
	public static final UnsignedByte ZERO = new UnsignedByte(0);

	public static UnsignedByte get(int x) {
		return new UnsignedByte(x);
	}

	private f  inal int value;

	private UnsignedByte(int x) {
		validate(x);
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
	public UnsignedByte add(UnsignedByte val) {
		int sum = (this.value + val.value) % 256;
		boolean overflow = sum < this.value;
		return UnsignedByte.get(sum);
	}

	/**
	 * Returns an UnsignedByte whose value is this - val.
	 */
	public UnsignedByte subtract(UnsignedByte val) {
		int difference = (255 + this.value - val.value) % 256;
		boolean underflow = difference > this.value;
		return UnsignedByte.get(difference);
	}

	public UnsignedByte increment() {
		return new UnsignedByte((value + 1) % 256);
	}

	public UnsignedByte decrement() {
		return new UnsignedByte((value + 255) % 256);
	}

	public int toInteger() {
		return value;
	}

	public byte toByte() {
		return (byte) value;
	}

	public String toHexString() {
		return Integer.toString(value, 16);
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
}
