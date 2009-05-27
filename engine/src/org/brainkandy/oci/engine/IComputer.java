package org.brainkandy.oci.engine;

import org.brainkandy.oci.math.UnsignedByte;

/**
 * Abstraction for a computer instance.
 *
 * <p>This abstraction contains sixteen registers and an accumulator, each
 * holding a single byte. There is an additional 256 bytes of memory for both
 * program and data storage.
 */
public interface IComputer {
	public static final int REGISTER_B = 11;
	public static final int REGISTER_C = 12;

	/**
	 * Assign a byte value to a register
	 * @param i register number, between 0 and 15.
	 * @param datum the byte value to assign to the register.
	 */
	void setRegister(int i, UnsignedByte datum);

	/**
	 * Get the byte value from a register
	 * @param i register number, between 0 and 15.
	 * @return the byte value from the register.
	 */
	UnsignedByte getRegister(int i);

	/**
	 * Assign a byte value to the accumulator
	 * @param datum the byte value to assign to the accumulator.
	 */
	void setAccumulator(UnsignedByte datum);

	/**
	 * Get the byte value from the accumulator
	 * @return the byte value from the accumualator.
	 */
	UnsignedByte getAccumulator();

	/**
	 * 
	 * @param programCounter
	 */
	void setProgramCounter(UnsignedByte programCounter);
	UnsignedByte getProgramCounter();

	/**
	 * Advance the program counter to the next position.
	 *
	 * @return the data element of the program counter before it is incremented.
	 */
	UnsignedByte advanceProgramCounter();
	void saveProgramCounter();
	UnsignedByte restoreProgramCounter();

	
	/**
	 * Return the data value at a certain 
	 * @param programCounter
	 * @return
	 */
	UnsignedByte getMemory(UnsignedByte offset);

	/**
	 * Stop the computer at the completion of the next instruction.
	 */
	void halt();

	/**
	 * Returns {@code true} if the computer is in a running state
	 */
	boolean isRunning();

	void reset();
	void run(IContext context);
	void setProgram(UnsignedByte... bytes);
}
