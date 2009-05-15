package org.brainkandy.oci.engine.impl;

public class OpCode {
	private final byte hexcode;
	private final IOperation operation;

	private OpCode(int hexcode, IOperation operation) {
		this.hexcode = (byte) hexcode;
		this.operation = operation;
	}

	public byte getHexcode() {
		return hexcode;
	}

	public IOperation getOperation() {
		return operation;
	}
}
