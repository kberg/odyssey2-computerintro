package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;

public class AdditionB implements IProgram {
  public byte[] getProgram() {
  	return Bytes.codify(
  	    "70,04,6b00,c0,63,10,c3,0b,63,2b,c3,e0,b1,c1,c2,1200");
  }
}
