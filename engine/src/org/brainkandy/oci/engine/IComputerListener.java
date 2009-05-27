package org.brainkandy.oci.engine;

public interface IComputerListener {
	void announce(IComputer computer, DebugCode code, Object parameter);
}
