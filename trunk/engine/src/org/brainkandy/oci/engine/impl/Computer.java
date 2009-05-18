package org.brainkandy.oci.engine.impl;

import java.util.Stack; 

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;

public class Computer implements IComputer {

	private static final OpCodes opcodes = new OpCodes();

	private static final int MEMORY_SIZE = 256;
	// The accumulator is stored as the 17th register.
	private final byte[] registers = new byte[17];
	private final byte[] memory = new byte[MEMORY_SIZE];
	private byte programCounter;
	private final Stack<Byte> callStack;
	private volatile boolean continueRunning;

	public Computer() {
		callStack = new Stack<Byte>();
		clear();
	}
	
	private void clear() {
		reset();
		for (int i = 0; i < memory.length; i++) {
			memory[i] = 0;
		}
	}

	public void reset() {
		callStack.clear();
		for (int i = 0; i < registers.length; i++) {
			registers[i] = 0;
		}
		setProgramCounter((byte) 0);
		continueRunning = true;
	}

	public byte getAccumulator() {
		return getRegister(16);
	}

	public byte getRegister(int i) {
		return registers[i];
	}

	public void setAccumulator(byte datum) {
		setRegister(16, datum);
	}

	public void setRegister(int i, byte value) {
		registers[i] = value;
	}

	public byte getMemory(byte offset) {
		return memory[offset];
	}


	public void setProgramCounter(byte programCounter) {
		this.programCounter = programCounter;
	}

	public byte getProgramCounter() {
		return programCounter;
	}

	public byte advanceProgramCounter() {
		byte programCounter = getProgramCounter();
		byte datum = getMemory(programCounter);
		setProgramCounter((byte) (programCounter + 1));
		return datum;
	}

	public void halt() {
		continueRunning = false;
	}

	public void saveProgramCounter() {
		callStack.add(programCounter);
	}

	public byte restoreProgramCounter() {
		// TODO Auto-generated method stub
		return -1;
	}

	public void run(IContext context) {
		reset();
		while(continueRunning) {
			byte opcode = advanceProgramCounter();
			IOperation operation = opcodes.get(opcode);
			operation.execute(this, context);
			postOp();
		}
	}

	/**
	 * Hack, work-around for debuggers to step in.
	 */
	protected void postOp() {
		// No-op
  }

	public void setProgram(byte... bytes) {
		for (int i = 0; i < bytes.length; i++) {
			memory[i] = bytes[i];
		}
	}
}
