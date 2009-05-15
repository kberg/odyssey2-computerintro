package org.brainkandy.oci.engine.impl;

import java.util.Stack; 

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;

public class Computer implements IComputer {

	private static final OpCodes opcodes = new OpCodes();

	private static final int DATA_SPACE_SIZE = 100;
	// The accumulator is stored as the 17th register.
	private final byte[] registers = new byte[17];
	private final byte[] program = new byte[DATA_SPACE_SIZE];
	private int programStep;
	private final Stack<Integer> callStack;
	private boolean continueRunning;

	public Computer() {
		callStack = new Stack<Integer>();
		clear();
	}
	
	private void clear() {
		reset();
		for (int i = 0; i < program.length; i++) {
			program[i] = 0;
		}
	}

	public void reset() {
		callStack.clear();
		for (int i = 0; i < registers.length; i++) {
			registers[i] = 0;
		}
		setProgramCounter(0);
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

	public byte getAtProgramStep(int programStep) {
		return program[programStep];
	}


	public void setProgramCounter(int programStep) {
		this.programStep = programStep;
	}

	public int getProgramStep() {
		return programStep;
	}

	public byte advanceProgramStep() {
		int programStep = getProgramStep();
		byte datum = getAtProgramStep(programStep);
		// TODO: programStep + 1 should be a method that includes a wraparound, for instance.
		setProgramCounter(programStep + 1);
		return datum;
	}

	public void halt() {
		continueRunning = false;
	}

	public void saveProgramCounter() {
		callStack.add(programStep);
	}

	public byte restoreProgramCounter() {
		// TODO Auto-generated method stub
		return -1;
	}

	public void run(IContext context) {
		reset();
		while(continueRunning) {
			byte opcode = advanceProgramStep();
			IOperation operation = opcodes.get(opcode);
			operation.execute(this, context);
		}
	}

	public void setProgram(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			program[i] = bytes[i];
		}
	}
}
