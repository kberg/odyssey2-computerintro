package org.brainkandy.oci.display;

import org.brainkandy.oci.context.IBitmap;
import org.brainkandy.oci.math.Bytes;

public class Char implements IBitmap {
	private byte[] bitmap;

	Char(String s) {
		this.bitmap = Bytes.codify(s);
	}

	public byte[] getBitmap() {
		return bitmap;
  }
}
