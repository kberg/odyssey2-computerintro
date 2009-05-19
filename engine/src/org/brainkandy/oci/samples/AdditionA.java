package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class AdditionA implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "70,04,e0,b1,6b00,c1,c2,1200");
  }
}
