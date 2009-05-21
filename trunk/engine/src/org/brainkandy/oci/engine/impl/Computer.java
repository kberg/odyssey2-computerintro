package org.brainkandy.oci.engine.impl;

import java.util.Stack; 

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.math.UnsignedByte;

public class Computer implements IComputer {

	private static final OpCodes opcodes = new OpCodes();

	private static final int MEMORY_SIZE = 100;
	private UnsignedByte accumulator;
	private final UnsignedByte[] registers = new UnsignedByte[16];
	private final UnsignedByte[] memory = new UnsignedByte[MEMORY_SIZE];
	private UnsignedByte programCounter;
	private final Stack<UnsignedByte> callStack;
	private volatile boolean continueRunning;

	public Computer() {
		callStack = new Stack<UnsignedByte>();
		clear();
	}
	
	private void clear() {
		reset();
		for (int i = 0; i < memory.length; i++) {
			memory[i] = UnsignedByte.ZERO;
		}
	}

	public void reset() {
		callStack.clear();
		for (int i = 0; i < registers.length; i++) {
			setRegister(i, UnsignedByte.ZERO);
		}
		setAccumulator(UnsignedByte.ZERO);
		setProgramCounter(UnsignedByte.ZERO);
		continueRunning = true;
	}

	public UnsignedByte getAccumulator() {
		return accumulator;
	}

	public UnsignedByte getRegister(int i) {
		return registers[i];
	}

	public void setAccumulator(UnsignedByte datum) {
		this.accumulator = datum;
	}

	public void setRegister(int i, UnsignedByte value) {
		registers[i] = value;
	}

	public UnsignedByte getMemory(UnsignedByte offset) {
		return memory[offset.toBcdNumber()];
	}


	public void setProgramCounter(UnsignedByte programCounter) {
		this.programCounter = programCounter;
	}

	public UnsignedByte getProgramCounter() {
		return programCounter;
	}

	public UnsignedByte advanceProgramCounter() {
		UnsignedByte programCounter = getProgramCounter();
		UnsignedByte datum = getMemory(programCounter);
		setProgramCounter(programCounter.bcdIncrement());
		return datum;
	}

	public boolean isRunning() {
		return continueRunning;
	}

	public void halt() {
		continueRunning = false;
	}

	public void saveProgramCounter() {
		callStack.add(programCounter);
	}

	public UnsignedByte restoreProgramCounter() {
		throw new RuntimeException("restoreProgramCounter not written");
	}

	public void run(IContext context) {
		reset();
		while(continueRunning) {
			UnsignedByte opcode = advanceProgramCounter();
			IOperation operation = opcodes.get(opcode);
			if (operation == null) {
				throw new IllegalArgumentException("Unknown Opcode " + opcode);
			}
			operation.execute(this, context);
		}
	}

	/**
	 * Hack, work-around for debuggers to step in.
	 */
	protected void postOp() {
		// No-op
  }

	public void setProgram(UnsignedByte... unsignedBytes) {
		for (int i = 0; i < unsignedBytes.length; i++) {
			memory[i] = unsignedBytes[i];
		}
	}
}
