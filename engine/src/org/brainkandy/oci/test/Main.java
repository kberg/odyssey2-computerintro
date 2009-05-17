package org.brainkandy.oci.test;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.impl.Computer;

public class Main {
	public static void main(String[] args) {
		sample1();
		sample2();
	}

	// This program reads the three digits into registers 0, 1 and 0, and
	// prints them back out.
	private static void sample1() {
		IComputer computer = new Computer();
		computer.setProgram(
			(byte) 0x70,
			(byte) 0x71,
			(byte) 0xc0,
			(byte) 0xc1,
			(byte) 0x70,
			(byte) 0xc0,
			(byte) 0xff);
		TestContext context = new TestContext(
		    (byte) 0xa1, (byte) 0x9b, (byte) 0x3c);
		computer.run(context);
		System.out.println(context.outputToString());
	}

	// Loads a value in the accumulator,
	// puts it in the 
	private static void sample2() {
		IComputer computer = new Computer();
		computer.setProgram(
			(byte) 0x70,
			(byte) 0xff,
			(byte) 0xc0,
			(byte) 0xc1,
			(byte) 0x70,
			(byte) 0xc0,
			(byte) 0xff);
		TestContext context = new TestContext(
		    (byte) 0xa1, (byte) 0x9b, (byte) 0x3c);
		computer.run(context);
		System.out.println(context.outputToString());
	}

	// Testing Gosub
	private static void sample3() {
		IComputer computer = new Computer();
		computer.setProgram(
			(byte) 0x70,
			(byte) 0xff,
			(byte) 0xc0,
			(byte) 0xc1,
			(byte) 0x70,
			(byte) 0xc0,
			(byte) 0xff);
		TestContext context = new TestContext(
		    (byte) 0xa1, (byte) 0x9b, (byte) 0x3c);
		computer.run(context);
		System.out.println(context.outputToString());
	}
}
