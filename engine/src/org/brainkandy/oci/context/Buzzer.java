package org.brainkandy.oci.context;

import org.brainkandy.oci.engine.IBuzzer;

public class Buzzer implements IBuzzer {
	public void buzz() {
		try {
			/// something
	    Thread.sleep(1000);
    } catch (InterruptedException e) {
    	Thread.currentThread().interrupt();
    	return;
    }
	}
}
