package org.brainkandy.oci.engine.impl;

import java.util.Random;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.math.UnsignedByte;

class Operations {
	private static class IncompleteOperationException extends RuntimeException {
		public IncompleteOperationException(String type) {
			super("Incomplete operation " + type);
		}
	}

	public static final IOperation NO_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
		}
	};

	public static final IOperation HALT_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			computer.halt();
		}
	};

	public static final IOperation GOSUB_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			computer.saveProgramCounter();
		}
	};

	public static final IOperation BRACH_DECIMAL_BORROW_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			throw new IncompleteOperationException("BRACH_DECIMAL_BORROW_OPERATION");
		}
	};

	public static final IOperation BRACH_DECIMAL_CARRY_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			throw new IncompleteOperationException("BRACH_DECIMAL_CARRY_OPERATION");
		}
	};

	public static final IOperation BRACH_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			UnsignedByte datum = computer.advanceProgramCounter();
			computer.setProgramCounter(datum);
		}
	};

	public static final IOperation BRACH_ACCUM_ZERO_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			if (computer.getAccumulator().equals(UnsignedByte.ZERO)) {
				UnsignedByte datum = computer.advanceProgramCounter();
				computer.setProgramCounter(datum);
			}
			throw new IncompleteOperationException("BRACH_ACCUM_ZERO");
		}
	};

	public static final IOperation RETURN_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			UnsignedByte value = computer.restoreProgramCounter();
			computer.setProgramCounter(value);
		}
	};

	public static final IOperation SIG_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			context.getBuzzer().buzz();
		}
	};

	public static final IOperation RND_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			// HACK. Move the Randomizer to the Computer, and then allow
			// a seed to be set right on the computer.
			// Plus, we don't know if this is correct.
			Random random = new Random();
			int first = random.nextInt(9);
			int second = random.nextInt(9);
			UnsignedByte b = UnsignedByte.get("" + first + second);
			computer.setAccumulator(b);
		}
	};

	public static IOperation inputOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setRegister(i, context.getInput().read());
			}
		};
	}

	public static final IOperation INPUT_ACCUM_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			computer.setAccumulator(context.getInput().read());
		}
	};

	public static final IOperation OUTPUT_ACCUM_OPERATION = outputOperation(-1);

	public static final IOperation CLEAR_ACCUM_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			computer.setAccumulator(UnsignedByte.ZERO);
		}
	};

	
	public static IOperation outputOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				UnsignedByte registerBValue = computer.getRegister(IComputer.REGISTER_B);
				context.getOutput().setPosition(registerBValue);
				UnsignedByte value = (i == -1 ? computer.getAccumulator() : computer.getRegister(i))
				;
				context.getOutput().put(value);
				registerBValue = registerBValue.bcdIncrement();
				if (registerBValue.toBcdNumber() >= 11) {
					registerBValue = UnsignedByte.ZERO;
				}
				
				computer.setRegister(IComputer.REGISTER_B, registerBValue);
			}
		};
	}

	public static IOperation packOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				throw new IncompleteOperationException("Pack");
			}
		};
	}

	public static IOperation unpackOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				UnsignedByte value = computer.getAccumulator();
				computer.setRegister(i, value.firstDigit());
				computer.setRegister((i + 1) % 16, value.secondDigit());
			}
		};
	}

	public static IOperation loadFromRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setAccumulator(computer.getRegister(i));
			}
		};
	}

	public static IOperation subtractFromRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				UnsignedByte accumulatorDatum = computer.getAccumulator();
				UnsignedByte registerDatum = computer.getRegister(i);
				UnsignedByte value = accumulatorDatum.bcdSubtract(registerDatum);
				computer.setAccumulator(value);
			}
		};
	}

	public static IOperation addFromRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				UnsignedByte accumulatorDatum = computer.getAccumulator();
				UnsignedByte registerDatum = computer.getRegister(i);
				UnsignedByte value = accumulatorDatum.bcdAdd(registerDatum);
				computer.setAccumulator(value);
			}
		};
	}

	public static IOperation storeInRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setRegister(i, computer.getAccumulator());
			}
		};
	}

	public static IOperation assignValueToRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				computer.setRegister(i, computer.advanceProgramCounter());
			}
		};
	}
}
