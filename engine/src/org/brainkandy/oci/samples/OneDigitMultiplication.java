package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class OneDigitMultiplication implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "6b00,70,c0,6629,c6,71,c1,672b,c7,90,e0,a2,91,02,a1,6301",
  	    "3325,92,1213,92,b4,c4,c5,1200");
  }
}
