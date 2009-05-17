package org.brainkandy.oci.context;

import org.brainkandy.oci.engine.IBuzzer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.IInput;
import org.brainkandy.oci.engine.IOutput;

public class DesktopContext implements IContext {

	public IBuzzer getBuzzer() {
		return new Buzzer();
  }

	public IInput getInput() {
	  // TODO Auto-generated method stub
	  return null;
  }

	public IOutput getOutput() {
	  // TODO Auto-generated method stub
	  return null;
  }

}
