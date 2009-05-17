package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;

public class AdditionA implements IProgram {
  public byte[] getProgram() {
  	return Bytes.codify(
  	    "70,04,e0,b1,6b00,c1,c2,1200");
  }
}
