package org.brainkandy.oci.engine;

public interface IOutput {
	void put(byte datum);
	void setPosition(byte position);
}
