package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class AdditionC implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "6b00,70,c0,6310,c3,04,0b,632b,c3,e0,b1,c1,c2,70,",
  	    "6c0b,670c,9c,6400,02,c7,2424,6b00,1203");
  }
}
