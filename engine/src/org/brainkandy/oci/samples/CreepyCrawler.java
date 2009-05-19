package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class CreepyCrawler implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "603a,610c,6b00,c0,c1,05,08,bb,1206");
  }
}
