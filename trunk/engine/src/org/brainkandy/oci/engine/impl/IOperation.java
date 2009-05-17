package org.brainkandy.oci.engine.impl;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;

public interface IOperation {
	void execute(IComputer computer, IContext context);
}
