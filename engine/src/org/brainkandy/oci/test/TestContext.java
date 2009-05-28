package org.brainkandy.oci.test;

import java.util.Arrays;

import org.brainkandy.oci.engine.IBuzzer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.IInput;
import org.brainkandy.oci.engine.IOutput;
import org.brainkandy.oci.math.UnsignedByte;

public class TestContext implements IContext {
  private final byte[] inputData;
  private int readPosition;
  private final UnsignedByte[] outputData = new UnsignedByte[13];
  private UnsignedByte writePosition;

  private final IInput input = new IInput() {
    public UnsignedByte read() {
      if (readPosition >= inputData.length) {
        return UnsignedByte.ZERO;
      } else {
        int datum = inputData[readPosition++];
        if (datum < 0) {
          datum = 256 - datum;
        }
        return UnsignedByte.get(datum);
      }
    }
  };

  private final IOutput output = new IOutput() {
    public void put(UnsignedByte datum) {
      outputData[writePosition.bcdGet()] = datum;
    }

    public void setPosition(UnsignedByte writePosition) {
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
    writePosition = UnsignedByte.ZERO;
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
