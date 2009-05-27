package org.brainkandy.oci.display;

import org.brainkandy.oci.context.IBitmap;

public class Chars {
	private static final Char[] chars = new Char[] {
		new Char("7cc6c6c6c6c67c"), // 0
		new Char("1838181818183c"), // 1
		new Char("3c660c1830607e"), // 2
		new Char("7cc6063c06c67c"), // 3
		new Char("ccccccfe0c0c0c"), // 4
		new Char("fec0c07c06c67c"), // 5
		new Char("7cc6c0fcc6c67c"), // 6
		new Char("fe060c183060c0"), // 7
		new Char("7cc6c67cc6c67c"), // 8
		new Char("7cc6c67e06c67c"), // 9
		new Char("00181800181800"), // :
		new Char("187e587e1a7e18"), // $
		new Char("00000000000000"), // space
		new Char("7cc60c18180018"), // ?
		new Char("c0c0c0c0c0c0fe"), // L
		new Char("f8c6c6f8c0c0c0"), // P
		new Char("0018187e181800"), // +
		new Char("c6c6c6d6feeec6"), // W
		new Char("fec0c0fcc0c0fe"), // E
		new Char("fcc6c6f8d8ccc6"), // R
		new Char("7e181818181818"), // T
		new Char("c6c6c6c6c6c67c"), // U
		new Char("3c18181818183c"), // I
		new Char("7cc6c6c6c6c67c"), // O
		new Char("7cc6c6c6decc76"), // Q
		new Char("7cc6c07c06c67c"), // S
		new Char("fcc6c6c6c6c6fc"), // D
		new Char("fec0c0fcc0c0c0"), // F
		new Char("7cc6c0c0cec67e"), // G
		new Char("c6c6c6fec6c6c6"), // H
		new Char("0606060606c67c"), // J
		new Char("c6ccd8f0d8ccc6"), // K
		new Char("386cc6c6fec6c6"), // A
		new Char("7e060c1830607e"), // Z
		new Char("c6c67c387cc6c6"), // X
		new Char("7cc6c0c0c0c67c"), // C
		new Char("c6c6c6c6c66c38"), // V
		new Char("fcc6c6fcc6c6fc"), // B
		new Char("c6eefed6c6c6c6"), // M
		new Char("00000000001818"), // .
		new Char("0000007c000000"), // -
		new Char("00663c183c6600"), // times operator
		new Char("0018007e001800"), // division operator
		new Char("00007e007e0000"), // equals
		new Char("6666663c181818"), // Y
		new Char("c6e6f6fedecec6"), // N
		new Char("03060c183060c0"), // /
		new Char("fefefefefefefe"), // block
		new Char("cedbdbdbdbdbce"), // ten
		new Char("00003c7e7e7e3c"), // ball
		new Char("1c1c181e18181c"), // dude facing right
		new Char("1c1c181e183426"), // dude walking right
		new Char("1c1c0c3c0c1632"), // dude walking left
		new Char("1c1c0c3c0c0c1c"), // dude facing left
		new Char("00180cfe0c1800"), // arrow
		new Char("183c7effff1818"), // tree
		new Char("03070f1f3f7fff"), // stairs up
		new Char("c0e0f0f8fcfeff"), // stairs down
		new Char("383812feb8286c"), // dude facing front
    new Char("c06030180c0603"), // \
		new Char("0000181010ff7c"), // submarine
		new Char("000363ffff1808"), // airplane
		new Char("0000001038fe7c"), // ship
	};

	public static Char[] getChars() {
		return chars;
	}

	public static IBitmap getChar(int index) {
		try {
	    return chars[index];
    } catch (ArrayIndexOutOfBoundsException e) {
    	if (index < 0) {
    		throw new ArrayIndexOutOfBoundsException(e.getMessage());
    	}
    	index = index & chars.length;
    	return mutate(chars[index], 1);
    }
	}

	// Kind of expensive just for a hack.
	private static IBitmap mutate(final IBitmap bitmap, final int rows) {
		return new IBitmap() {
			public byte[] getBitmap() {
				byte[] array = new byte[bitmap.getBitmap().length];
				System.arraycopy(bitmap.getBitmap(), rows, array, 0, array.length - rows);
				return array;
      }
		};
  }

	public static Char getChar(char c) {
		int index = getCharIndex(c);
		return index == -1 ? null : Chars.chars[index];
	}

	public static int getCharIndex(char c) {
		for (int i = 0; i < Chars.asciiMap.length; i++) {
			if (c == Chars.asciiMap[i]) {
				return i;
			}
		}
		return -1;
	}

	public static final char[] asciiMap = 
		  "0123456789:$ ?LP+WERTUIOQSDFGHJKAZXCVBM.-`~=YN/*_,[{}]>^'\"|\\#()".toCharArray();
}
