package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class OneDigitDivision implements IProgram {
  public UnsignedByte[] getProgram() {
  	return Bytes.codify(
  	    "6300,6b00,70,c0,692a,c9,71,c1,6a2b,ca,91,d0,a0,93,03,a3,90,1340,91",
  	    "5028,1215,93,b4,c4,c5,6610,6713,c6,c7,1200",
  	    "93,b4,c4,c5,660c,670c,c6,c7,1200");
  }
}
