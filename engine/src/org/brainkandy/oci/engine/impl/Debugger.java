package org.brainkandy.oci.engine.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IDebugger;

public class Debugger implements IDebugger {

  private final IComputer computer;

  private final Set<Integer> breakpoints = new CopyOnWriteArraySet<Integer>();

  private volatile boolean pausing;

  public Debugger(IComputer computer) {
    this.computer = computer;
  }

  public void reset() {
    pausing = false;
  }

  public boolean keepRunning() {
    return !pausing;
  }

  public void run() {
    while (keepRunning()) {
      if (breakpoints.contains(new Integer(computer.getProgramCounter()
          .bcdGet()))) {
        pause();
      }
      step();
    }
  }

  public void addBreakpoint(int breakpoint) {
    breakpoints.add(breakpoint);
  }

  public void removeBreakpoint(int breakpoint) {
    breakpoints.remove(breakpoint);
  }

  public void clearBreakpoints() {
    breakpoints.clear();
  }

  public void pause() {
    pausing = true;
  }

  public void step() {
    computer.step();
  }

  public void halt() {
    computer.halt();
  }

  // Returns in order, which is kind of nice.
  public int[] getBreakpoints() {
    int[] array = new int[breakpoints.size()];
    int i = 0;
    for(Integer breakpoint : breakpoints) {
      array[i++] = breakpoint.intValue();
    }
    Arrays.sort(array);
    return array;
  }
}
