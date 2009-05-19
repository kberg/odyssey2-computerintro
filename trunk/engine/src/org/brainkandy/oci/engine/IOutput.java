package org.brainkandy.oci.engine;

import org.brainkandy.oci.math.UnsignedByte;

public interface IOutput {
	void put(UnsignedByte datum);
	void setPosition(UnsignedByte position);
}
