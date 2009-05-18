package org.brainkandy.oci.engine.impl;

import static org.brainkandy.oci.engine.impl.Operations.BRACH_ACCUM_ZERO_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.BRACH_DECIMAL_BORROW_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.BRACH_DECIMAL_CARRY_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.BRACH_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.CLEAR_ACCUM_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.GOSUB_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.HALT_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.INPUT_ACCUM_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.NO_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.OUTPUT_ACCUM_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.RETURN_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.RND_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.SIG_OPERATION;
import static org.brainkandy.oci.engine.impl.Operations.addToRegisterOperation;
import static org.brainkandy.oci.engine.impl.Operations.assignValueToRegisterOperation;
import static org.brainkandy.oci.engine.impl.Operations.inputOperation;
import static org.brainkandy.oci.engine.impl.Operations.loadFromRegisterOperation;
import static org.brainkandy.oci.engine.impl.Operations.outputOperation;
import static org.brainkandy.oci.engine.impl.Operations.packOperation;
import static org.brainkandy.oci.engine.impl.Operations.storeInRegisterOperation;
import static org.brainkandy.oci.engine.impl.Operations.subtractFromRegisterOperation;
import static org.brainkandy.oci.engine.impl.Operations.unpackOperation;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;

public class OpCodes {
	private final IOperation[] operations = new IOperation[256];

	public OpCodes() {
		for (int i = 0; i < 16; i++) {
			add((byte) (0x70 + i), inputOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0xC0 + i), outputOperation(i));
		}

		add((byte) 0x04, INPUT_ACCUM_OPERATION);
		add((byte) 0x0b, OUTPUT_ACCUM_OPERATION);
		add((byte) 0x05, SIG_OPERATION);
		add((byte) 0x01, CLEAR_ACCUM_OPERATION);
		add((byte) 0x02, new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setAccumulator((byte) (computer.getAccumulator() - 1));
			}
		});
		add((byte) 0x03, new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setAccumulator((byte) (computer.getAccumulator() + 1));
			}
		});
		add((byte) 0x08, RND_OPERATION);
		add((byte) 0x09, new IOperation() {
			public void execute(IComputer computer, IContext context) {
				byte register = computer.getRegister(IComputer.REGISTER_B);
				byte datum = computer.getMemory(register);
				computer.setAccumulator(datum);
			}
		});
		for (int i = 0; i < 16; i++) {
			add((byte) (0x80 + i), packOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0xB0 + i), unpackOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0x90 + i), loadFromRegisterOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0xD0 + i), subtractFromRegisterOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0xE0 + i), addToRegisterOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0xA0 + i), storeInRegisterOperation(i));
		}
		for (int i = 0; i < 16; i++) {
			add((byte) (0x60 + i), assignValueToRegisterOperation(i));
		}
		add((byte) 0x00, NO_OPERATION);
		add((byte) 0xFF, HALT_OPERATION);
		add((byte) 0x14, GOSUB_OPERATION);
		add((byte) 0x07, RETURN_OPERATION);
		add((byte) 0x10, BRACH_DECIMAL_BORROW_OPERATION);
		add((byte) 0x11, BRACH_DECIMAL_CARRY_OPERATION);
		add((byte) 0x12, BRACH_OPERATION);
		add((byte) 0x13, BRACH_ACCUM_ZERO_OPERATION);
	}

	private void add(byte i, IOperation operation) {
		operations[i + 128] = operation;
	}

	public IOperation get(byte opcode) {
		return operations[opcode + 128];
	}
}
