package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class CreepyCrawler2 implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "6213,08,bb,9c,e2,ac,09,0b,05,00,1202,",
  	    "32,33,3a,34,35,37,3d,3e,36,3c");
  }
}
