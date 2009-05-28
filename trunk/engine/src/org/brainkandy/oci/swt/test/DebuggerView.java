package org.brainkandy.oci.swt.test;

import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.math.UnsignedByte;
import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.swt.widgets.Shell;

public class DebuggerView {
  private final Shell shell;
  private final SwtDisplay swtDisplay;

  public DebuggerView(Shell shell, SwtDisplay swtDisplay) {
    this.shell = shell;
    this.swtDisplay = swtDisplay;
    initializeView();
  }

  private void initializeView() {
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

  public void showRunningState(String s) {
    printString(7, 1, s);
  }

  public void showReigster(int i, UnsignedByte value) {
    int column = i <= 7 ? 28 : 34;
    int row = i % 8;
    value = value == null ? UnsignedByte.ZERO : value;
    printString(column, row, value.toString());
  }

  public void showAccum(UnsignedByte value) {
    printString(8, 3, value.toString());
  }

  public void showProgramCounter(UnsignedByte programCounter) {
    printString(8, 2, programCounter.toString());
  }
}
