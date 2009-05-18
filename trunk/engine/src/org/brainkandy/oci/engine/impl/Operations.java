package org.brainkandy.oci.engine.impl;

import java.util.Random;

import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.math.BCD;

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
			byte datum = computer.advanceProgramCounter();
			computer.setProgramCounter(datum);
		}
	};

	public static final IOperation BRACH_ACCUM_ZERO_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			if (computer.getAccumulator() == 0) {
				byte datum = computer.advanceProgramCounter();
				computer.setProgramCounter(datum);
			}
			throw new IncompleteOperationException("BRACH_ACCUM_ZERO");
		}
	};

	public static final IOperation RETURN_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			byte value = computer.restoreProgramCounter();
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
			byte b = (byte) random.nextInt(99);
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

	public static final IOperation OUTPUT_ACCUM_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			context.getOutput().put(computer.getAccumulator());
		}
	};

	public static final IOperation CLEAR_ACCUM_OPERATION = new IOperation() {
		public void execute(IComputer computer, IContext context) {
			computer.setAccumulator((byte) 0);
		}
	};

	public static IOperation outputOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				byte registerBValue = computer.getRegister(11);
				context.getOutput().setPosition(registerBValue);
				context.getOutput().put(computer.getRegister(i));
				registerBValue = (byte) (registerBValue + 1);
				// Wha?
				if (registerBValue == 13) {
					registerBValue = 0;
				}
				
				computer.setRegister(11, registerBValue);
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
				byte value = computer.getAccumulator();
				byte[] split = BCD.split(value);
				computer.setRegister(i, split[0]);
				computer.setRegister((i + 1 % 16), split[1]);
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
				byte accumulatorDatum = computer.getAccumulator();
				byte registerDatum = computer.getRegister(i);
				byte value = (byte) (accumulatorDatum - registerDatum);
				computer.setAccumulator(value);
			}
		};
	}

	public static IOperation addToRegisterOperation(final int i) {
		return new IOperation() {
			public void execute(IComputer computer, IContext context) {
				byte accumulatorDatum = computer.getAccumulator();
				byte registerDatum = computer.getRegister(i);
				byte value = (byte) (accumulatorDatum + registerDatum);
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
