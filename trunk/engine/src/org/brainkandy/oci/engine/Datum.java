package org.brainkandy.oci.engine;

public class Datum {

	// Flyweight pattern.
	private static final Datum[] DATA = new Datum[256];
	static {
		for(int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
			DATA[i + 128] = new Datum((byte) i);
		}
	}

	public static final Datum ZERO = new Datum((byte) 0);

	public final byte value;

	private Datum(byte value) {
		this.value = value;
	}

	public Datum increment() {
		return new Datum((byte) (value + 1));
	}

	public Datum decrement() {
		return new Datum((byte) (value - 1));
	}

	@Override
	public String toString() {
		return Byte.toString(value);
	}

	public static Datum get(byte value) {
		return DATA[value + 128];
	}
}
