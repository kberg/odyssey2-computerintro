package org.brainkandy.oci.display;

import org.brainkandy.oci.context.IBitmap;
import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public class Char implements IBitmap {
	private byte[] bitmap;

	Char(String s) {
		UnsignedByte[] codified = Bytes.codify(s);
		this.bitmap = new byte[codified.length];
		int i = 0;
		for (UnsignedByte unsignedByte : codified) {
			bitmap[i++] = unsignedByte.toByte();
		}
	}

	public byte[] getBitmap() {
		return bitmap;
  }
}
