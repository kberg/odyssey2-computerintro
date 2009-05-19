package org.brainkandy.oci.test;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.impl.Computer;
import org.brainkandy.oci.math.Bytes;

public class Main {
	public static void main(String[] args) {
		sample1();
		sample2();
	}

	// This program reads the three digits into registers 0, 1 and 0, and
	// prints them back out.
	private static void sample1() {
		IComputer computer = new Computer();
		computer.setProgram(Bytes.codify(
				"70,71,c0,c1,70,c0,ff"
				));
		TestContext context = new TestContext(
		    (byte) 0xa1, (byte) 0x9b, (byte) 0x3c);
		computer.run(context);
		System.out.println(context.outputToString());
	}

	// Loads a value in the accumulator,
	// puts it in the 
	private static void sample2() {
		IComputer computer = new Computer();
		computer.setProgram(Bytes.codify(
				"70ffc0c170c0ff"));
		TestContext context = new TestContext(
		    (byte) 0xa1, (byte) 0x9b, (byte) 0x3c);
		computer.run(context);
		System.out.println(context.outputToString());
	}
}
