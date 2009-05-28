package org.brainkandy.oci.display;

import org.brainkandy.oci.context.IBitmap;

// Assuming 8x8 characters.
public interface IDisplay {
  void setPosition(int x, int y);

  int getXPosition();

  int getYPosition();

  void print(IBitmap c);

  void advanceCursor();
}
