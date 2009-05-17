package org.brainkandy.oci.math;

public class Bytes {

	public static byte[] codify(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			sb.append(string);
		}
		// Remove all commas, spaces and colons, useful separators
		String s = sb.toString().replaceAll("[, :]", "");
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			int idx = i / 2;
			byte value = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
			    .digit(s.charAt(i + 1), 16));
			data[idx] = value;
		}
		return data;
	}

}
