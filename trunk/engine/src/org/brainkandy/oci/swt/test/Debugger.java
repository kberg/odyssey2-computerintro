package org.brainkandy.oci.swt.test;

import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.impl.Computer;
import org.brainkandy.oci.math.UnsignedByte;
import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.swt.widgets.Shell;

/**
 * Typically I wouldn't use inheritence to implement a debugger, but the truth
 * is it works rather well for small execution frameworks. Sure a listening
 * mechanism would be better, but this works just fine. Feel free to improve
 * this!
 */
public class Debugger extends Computer {

	private final Shell shell;
	private final SwtDisplay swtDisplay;

	public Debugger(Shell shell, SwtDisplay swtDisplay) {
		this.shell = shell;
		this.swtDisplay = swtDisplay;
		initializeDisplay();
	}

	private void initializeDisplay() {
		printString(0, 0, "Odyssey 2 Computer Intro!");
		printString(0, 1, "State: Waiting");
		printString(0, 3, "Accum: ");
		printString(0, 2, "PC: ");
		printString(25, 0, "0:");
		printString(25, 1, "1:");
		printString(25, 2, "2:");
		printString(25, 3, "3:");
		printString(25, 4, "4:");
		printString(25, 5, "5:");
		printString(25, 6, "6:");
		printString(25, 7, "7:");
		printString(31, 0, "8:");
		printString(31, 1, "9:");
		printString(31, 2, "A:");
		printString(31, 3, "B:");
		printString(31, 4, "C:");
		printString(31, 5, "D:");
		printString(31, 6, "E:");
		printString(31, 7, "F:");
	}

	private void printString(final int x, final int y, final String string) {
		if (shell == null) {
			return;
		}
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				swtDisplay.setPosition(x, y);
				for (char ch : string.toCharArray()) {
					swtDisplay.print(Chars.getChar(Character.toUpperCase(ch)));
				}
			}
		});
	}

	@Override
	public void halt() {
		super.halt();
		showRunningState(false);
	}

	private void showRunningState(boolean b) {
		printString(7, 1, b ? "Runn" : "Wait");
	}

	// @Override
	// public void reset() {
	// // TODO Auto-generated method stub
	// super.reset();
	// }
	//
	// @Override
	// public byte restoreProgramCounter() {
	// // TODO Auto-generated method stub
	// return super.restoreProgramCounter();
	// }
	//

	@Override
	public void run(IContext context) {
		super.run(context);
		showRunningState(true);
	}

	// @Override
	// public void saveProgramCounter() {
	// // TODO Auto-generated method stub
	// super.saveProgramCounter();
	// }

	@Override
	public void setAccumulator(UnsignedByte datum) {
		super.setAccumulator(datum);
		printString(8, 3, datum.toHexString());
	}

	// @Override
	// public void setProgram(UnsignedByte... bytes) {
	// // TODO Auto-generated method stub
	// super.setProgram(UnsignedBytes);
	// }

	@Override
	public void setProgramCounter(UnsignedByte programCounter) {
		super.setProgramCounter(programCounter);
		printString(8, 2, programCounter.toHexString());
	}

	@Override
	public void setRegister(int i, UnsignedByte value) {
		super.setRegister(i, value);
		int column = i <= 7 ? 28 : 34;
		int row = i % 8;
		printString(column, row, value.toHexString());
	}

	@Override
	protected void postOp() {
		@SuppressWarnings("unused")
    int x = 1 + 1;
	}
}
