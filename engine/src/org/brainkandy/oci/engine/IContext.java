package org.brainkandy.oci.engine;

public interface IContext {
  IOutput getOutput();

  IInput getInput();

  IBuzzer getBuzzer();
}
