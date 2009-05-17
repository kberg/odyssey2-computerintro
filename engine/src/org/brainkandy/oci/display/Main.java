package org.brainkandy.oci.display;

public class Main {
	public static void main(String[] args) {
		for(Char c : Chars.chars) {
			show(c);
		}
	}

	private static void show(Char c) {
		for (byte b : c.getBitmap()) {
	    print(b);
    }
		System.out.println();
  }

	private static void print(int b) {
		int mask = 0x80;
		for (int i = 0; i < 8; i++) {
			int masked = b & mask;
			System.out.print(masked != 0 ? "*" : " ");
			mask >>= 1;
		}
		System.out.println();
  }
}
