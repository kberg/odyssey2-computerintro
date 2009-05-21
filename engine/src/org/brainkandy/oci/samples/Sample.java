package org.brainkandy.oci.samples;

import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;

public enum Sample implements IProgram {
	CreepyCrawler("Creepy Crawler",
			"603a,610c,6b00,c0,c1,05,08,bb,1206"),

	// TODO: Seems to only occupy 9 spaces on the screen.
	CreepyCrawler2("Creepy Crawler 2",
			"6213,08,bb,9c,e2,ac,09,0b,05,00,1202,",
	    "32,33,3a,34,35,37,3d,3e,36,3c"),
  AdditionA("Addition A",
  		"70,04,e0,b1,6b00,c1,c2,1200"),
  AdditionB("Addition B",
  		"70,04,6b00,c0,63,10,c3,0b,63,2b,c3,e0,b1,c1,c2,1200"),
  AdditionC("Addition C",
  		"6b00,70,c0,6310,c3,04,0b,632b,c3,e0,b1,c1,c2,70,",
	    "6c0b,670c,9c,6400,02,c7,2424,6b00,1203"),
	OneDigitMultiplication("One Digit Multiplication",
			"6b00,70,c0,6629,c6,71,c1,672b,c7,90,e0,a2,91,02,a1,",
			"6301,3325,92,1213,92,b4,c4,c5,1200"),
	OneDigitDivision("One Digit Division",
			"6300,6b00,70,c0,692a,c9,71,c1,6a2b,ca,91,d0,a0,93,03,a3,90",
			"1340,91,5028,1215,93,b4,c4,c5,6610,6713,c6,c7,1200",
	    ",93,b4,c4,c5,660c,670c,c6,c7,1200"),
	GotoSubroutineAndReturn("Go to Subroutine and Return",
			"6B00 6101 6202 73 74 04 3113 3220 1466 B5 C5 C6",
			"1258 1466 00 A3 6402 6700 94 D3 A3 97 03 A7 93 1354 94 5342",
			"1229 97 B8 C8 C9 6E10 6F13 CE CF 1258 97 B8 C8 C9 6E0C 6F0C",
			"CE CF 1200 93 E3 A5 94 02 A4 3177",
			"95 1267 95 07"),
	OneDigitAdditionFlashCard("One Digit Addition Flash Card",
			"6A0C 6810 692B 6C2D 6D17 08 B0 6B00 C0 C8 C1 00",
			"C9 90 E1 A2 73 C3 74 C4 83 CA 3245 CC CD 6B04 73",
			"C3 CA CA 00 CA CA 6B05 1224 05 1470 05 05 05 1470",
			"05 1470 1470 05 1470 05 6B00 CA 9B 1310 1264 6700",
			"6E75 9E 00 02 2775 07"),
	SixLetterGuess("Six Letter Guess", 
	    "6701 6B00 79 6127 C1 7A 6227 C2 7C 6327 C3 7D 6427 C4 7E 6527",
	    "C5 7F 6627 C6 6B00 C1 C2 C3 00 C4 C5 C6 00 3700 04 3955 3A58",
	    "3C61 3D64 3E67 3F70 1271 A1 1243 A2 1245 A3 1247 A4 1249 A5",
	    "1251 A6 6827 98 3128 3228 3328 3428 3528 3628 672B 97 05 05 1228"),
	Message("Message",
	    "6090 610C 04 E0 AC 09 AC 6B00 6211 6300 91 0B 92 02 A2 2315 6B00",
	    "09 3304 0B 1224",
	    "1D 12 0E 0E 17 00",
	    "14 20 1F 12 0C 20 0C 0E 17 17 1F 00",
	    "13 12 26 20 13 1F 20 25 0E 12 00",
	    "2D 12 11 0C 1B 17 13 0C 07 08 00",
	    "18 15 11 19 14 16 17 2D 19 0D 00",
	    "23 17 26 12 0C 25 20 23 1F 00",
	    "30 36 48 59 70 81"),
  ;

	private final String name;
	private final String source;
	private Sample(String name, String... sources) {
		this.name = name;
		StringBuilder sb = new StringBuilder();
		for (String s : sources) { sb.append(s); };
		this.source = sb.toString();
	}

	public String getName() {
  	return name;
  }

	public UnsignedByte[] getProgram() {
		return Bytes.codify(source);
  }

	public String getSource() {
		return source;
  }

}
