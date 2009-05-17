package org.brainkandy.oci.swt.test;

import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
	public static void main(String[] args) {
		Image image = new Image(null, 20, 20);
		Color red = new Color(null, 255, 0, 0);
		GC gc = new GC(image);
		gc.setBackground(red);
		gc.fillRectangle(image.getBounds());
		gc.dispose();
		red.dispose();

		Shell shell = new Shell();
		SwtDisplay swtDisplay = new SwtDisplay(shell, 25, 40);
		new SwtDisplayDressing(shell, swtDisplay);

		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
	}
}