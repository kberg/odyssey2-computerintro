package org.brainkandy.oci.engine;

public interface IDebugger {

  void reset();

  void run();

  void addBreakpoint(int breakpoint);

  void removeBreakpoint(int breakpoint);

  void clearBreakpoints();

  void halt();

  void step();

  void pause();

  int[] getBreakpoints();
}
