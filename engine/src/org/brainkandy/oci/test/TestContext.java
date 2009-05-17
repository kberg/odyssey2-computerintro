package org.brainkandy.oci.test;

import java.util.Arrays;

import org.brainkandy.oci.engine.IBuzzer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.IInput;
import org.brainkandy.oci.engine.IOutput;

public class TestContext implements IContext {
	private final byte[] inputData;
	private int readPosition;
	private final byte[] outputData = new byte[13];
	private byte writePosition;

	private final IInput input = new IInput() {
		public byte read() {
			if (readPosition >= inputData.length) {
				return 0;
			} else {
				return inputData[readPosition++];
			}
		}
	};

	private final IOutput output = new IOutput() {
		public void put(byte datum) {
			outputData[writePosition] = datum;
		}

		public void setPosition(byte writePosition) {
			TestContext.this.writePosition = writePosition;
		}
	};

	private final IBuzzer buzzer = new IBuzzer() {
		public void buzz() {
			System.out.println("BUZZ!");
		}
	};

	public TestContext(byte... inputData) {
		this.inputData = new byte[inputData.length];
		System.arraycopy(inputData, 0, this.inputData, 0, inputData.length);
		readPosition = 0;
		writePosition = 0;
	}

	public IInput getInput() {
		return input;
	}

	public IOutput getOutput() {
		return output;
	}

	public String outputToString() {
		return Arrays.toString(outputData);
	}

	public IBuzzer getBuzzer() {
		return buzzer;
	}
}
