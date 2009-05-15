package org.brainkandy.oci.engine;

public interface IComputer {
	public static final int REGISTER_C = 12;
	void setRegister(int i, byte datum);
	byte getRegister(int i);
	void setAccumulator(byte datum);
	byte getAccumulator();
	void setProgramCounter(int programStep);
	int getProgramStep();
	byte getAtProgramStep(int programStep);
	void halt();
	byte advanceProgramStep();
	void saveProgramCounter();
	byte restoreProgramCounter();
	void reset();
	void run(IContext context);
	void setProgram(byte... bytes);
}
