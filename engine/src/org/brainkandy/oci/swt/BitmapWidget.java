package org.brainkandy.oci.swt;

import org.brainkandy.oci.context.IBitmap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class BitmapWidget extends Canvas {
	// TODO: add MULTIPLIER as parameter
	// TODO: add color
	// TODO: honor wHint and hHint in computeSize and possibly paint?
	private static final int MULTIPLIER = 3;

	private final int width;
	private final int height;
	private IBitmap bitmap;

	public BitmapWidget(Composite parent, int style, int width, int height) {
		super(parent, style);
		this.width = width;
		this.height = height;

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (bitmap != null) {
					_paintControl(e.gc);
				}
			}
		});
		setSize(width, height); // required?
	}

	public void setBitmap(IBitmap bitmap) {
		this.bitmap = bitmap;
		redraw();
	}

	public IBitmap getBitmap() {
		return bitmap;
	}

	@Override
  public Point computeSize(int wHint, int hHint, boolean changed) {
		// I wonder if, when we add a border, if this changes. Could be!
		return new Point(2 + (width * MULTIPLIER), 2 + (height * MULTIPLIER));
	}

	private void _paintControl(GC gc) {
		gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		int y = 0;
		for (byte b : bitmap.getBitmap()) {
			int x = 0;
			int mask = 0x80;
			for (int i = 0; i < height; i++) {
				int masked = b & mask;
				if (masked != 0) {
					gc.fillRectangle(x, y, MULTIPLIER, MULTIPLIER);
				}
				mask >>= 1;
				x += MULTIPLIER;
			}
			y += MULTIPLIER;
		}
	}
}
