package org.brainkandy.oci.swt.test;

import java.util.Set;
import java.util.TreeSet;

import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.engine.DebugCode;
import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IComputerListener;
import org.brainkandy.oci.math.UnsignedByte;
import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.swt.widgets.Shell;

public class Debugger implements IComputerListener {

	private final Shell shell;
	private final SwtDisplay swtDisplay;

	private final Set<Integer> breakpoints = new TreeSet<Integer>();

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

	public void announce(IComputer computer, DebugCode code, Object data) {
		switch(code) {
			case HALT:
				showRunningState("Stopped");
				break;
	
			case RUN:
				showRunningState("Running");
				break;
	
			case ACCUM_SET:
				printString(8, 3, computer.getAccumulator().toString());
				break;
	
			case PROGRAM_COUNTER_CHANGE:
				printString(8, 2, computer.getProgramCounter().toString());
				break;
	
			case REGISTER_SET: {
				int i = (Integer) data;
				int column = i <= 7 ? 28 : 34;
				int row = i % 8;
				UnsignedByte value = computer.getRegister(i);
				value = value == null ? UnsignedByte.ZERO : value;
				printString(column, row, value.toString());
				break;
			}
	  }
	}

	private void showRunningState(String s) {
		printString(7, 1, s);
	}
}
