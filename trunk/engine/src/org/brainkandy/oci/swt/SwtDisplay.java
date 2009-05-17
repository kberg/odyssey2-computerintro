package org.brainkandy.oci.swt;

import org.brainkandy.oci.context.IBitmap; 
import org.brainkandy.oci.display.IDisplay;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class SwtDisplay implements IDisplay {

	private int x;
	private int y;

	private final ConsoleWidget consoleWidget;

	public SwtDisplay(Composite parent, int rows, int columns) {
		consoleWidget = new ConsoleWidget(parent, 0, rows, columns, 8, 8);
		Point size = consoleWidget.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		consoleWidget.setSize(size);
	}

	public int getXPosition() {
		return x;
	}

	public int getYPosition() {
		return y;
	}

	public void print(IBitmap bitmap) {
	  consoleWidget.setBitmap(x, y, bitmap);
		advanceCursor();
	}

	public void setPosition(int x, int y) {
		if (x < 0 || x >= consoleWidget.getColumns()) {
			throw new IllegalArgumentException("x out of bounds");
		}
		if (y < 0 || y >= consoleWidget.getRows()) {
			throw new IllegalArgumentException("y out of bounds");
		}
		this.x = x;
		this.y = y;
	}

	public void advanceCursor() {
		x++;
		if (x >= consoleWidget.getColumns()) {
			x = 0;
			y++;
		}
		if (y >= consoleWidget.getRows()) {
			y = 0;
		}
	}
}
