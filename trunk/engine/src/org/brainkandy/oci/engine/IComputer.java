package org.brainkandy.oci.engine;

/**
 * Abstraction for a computer instance.
 *
 * <p>This abstraction contains sixteen registers and an accumulator, each
 * holding a single byte. There is an additional 256 bytes of memory for both
 * program and data storage.
 */
public interface IComputer {
	public static final int REGISTER_B = 11;

	/**
	 * Assign a byte value to a register
	 * @param i register number, between 0 and 15.
	 * @param datum the byte value to assign to the register.
	 */
	void setRegister(int i, byte datum);

	/**
	 * Get the byte value from a register
	 * @param i register number, between 0 and 15.
	 * @return the byte value from the register.
	 */
	byte getRegister(int i);

	/**
	 * Assign a byte value to the accumulator
	 * @param datum the byte value to assign to the accumulator.
	 */
	void setAccumulator(byte datum);

	/**
	 * Get the byte value from the accumulator
	 * @return the byte value from the accumualator.
	 */
	byte getAccumulator();

	/**
	 * 
	 * @param programCounter
	 */
	void setProgramCounter(byte programCounter);
	byte getProgramCounter();

	/**
	 * Return the data value at a certain 
	 * @param programCounter
	 * @return
	 */
	byte getMemory(byte offset);

	/**
	 * Stop the computer at the completion of the next instruction.
	 */
	void halt();

	/**
	 * Advance the program counter to the next position.
	 *
	 * @return the data element of the program counter before it is incremented.
	 */
	byte advanceProgramCounter();
	void saveProgramCounter();
	byte restoreProgramCounter();
	void reset();
	void run(IContext context);
	void setProgram(byte... bytes);
}
