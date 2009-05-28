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
      UnsignedByte value = UnsignedByte.get(s.substring(i, i + 2));
      data[idx] = value;
    }
    return data;
  }

}
