package org.brainkandy.oci.swt.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
  public static void main(String[] args) {
    Shell shell = new Shell();
    AdvancedSwtDisplayDressing stuff = new AdvancedSwtDisplayDressing(shell);

    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      try {
        if (!display.readAndDispatch()) {
          display.sleep();
        }
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }
    System.exit(0);
  }
}