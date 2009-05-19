package org.brainkandy.oci.math;

public class Bytes {

	public static UnsignedByte[] codify(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			sb.append(string);
		}
		// Remove all commas, spaces and colons, useful separators
		String s = sb.toString().replaceAll("[, :]", "");
		int len = s.length();
		UnsignedByte[] data = new UnsignedByte[len / 2];
		for (int i = 0, idx = 0; i < len; i += 2, idx++) {
			int sixteens = Character.digit(s.charAt(i), 16) << 4;
			int ones = Character.digit(s.charAt(i + 1), 16);
			UnsignedByte value = UnsignedByte.get(sixteens + ones);
			data[idx] = value;
		}
		return data;
	}

}
