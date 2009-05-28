package org.brainkandy.oci.swt;

import org.brainkandy.oci.context.IBitmap;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ConsoleWidget extends Composite {
  private final int rows;
  private final int columns;
  private final int width;
  private final int height;
  private final BitmapWidget[] widgets;

  public ConsoleWidget(Composite parent, int style, int rows, int columns,
      int width, int height) {
    // TODO: validate parameters (e.g. > zero)
    super(parent, style);
    this.rows = rows;
    this.columns = columns;
    this.width = width;
    this.height = height;

    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = columns;
    gridLayout.makeColumnsEqualWidth = true;
    gridLayout.horizontalSpacing = 0;
    gridLayout.verticalSpacing = 0;
    setLayout(gridLayout);

    widgets = new BitmapWidget[rows * columns];
    for (int i = 0; i < widgets.length; i++) {
      widgets[i] = new BitmapWidget(this, 0, width, height);
    }
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public int getCellWidth() {
    return width;
  }

  public int getCellHeight() {
    return height;
  }

  // TODO: validate x and y, throw exceptions if not valid.
  public IBitmap getBitmap(int x, int y) {
    int offset = calculateOffset(x, y);
    return widgets[offset].getBitmap();
  }

  public void setBitmap(int x, int y, IBitmap bitmap) {
    int offset = calculateOffset(x, y);
    widgets[offset].setBitmap(bitmap);
  }

  private int calculateOffset(int x, int y) {
    return x + (y * columns);
  }

  public void setBitmapByOffset(int offset, IBitmap bitmap) {
    widgets[offset].setBitmap(bitmap);
  }
}
