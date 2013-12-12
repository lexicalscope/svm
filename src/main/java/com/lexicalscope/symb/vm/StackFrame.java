package com.lexicalscope.symb.vm;

import static java.util.Arrays.copyOf;

public final class StackFrame {
	private final Object[] stack;
	private InstructionNode instruction; // PC
	private final int opBot; // pointer to bottom of operand stack
	private int opTop; // pointer to top of operand stack
	private final int vars = 0; // pointer to local variables

	public StackFrame(
			final InstructionNode instruction,
			final int maxLocals,
			final int maxStack) {
		this(instruction, new Object[maxLocals + maxStack], maxLocals - 1, maxLocals - 1);
	}

	private StackFrame(
	      final InstructionNode instruction,
	      final Object[] stack,
	      final int opBot,
	      final int opTop) {
		this.instruction = instruction;
		this.stack = stack;
      this.opBot = opBot;
		this.opTop = opTop;
	}

	public StackFrame advance(final InstructionNode nextInstruction) {
		instruction = nextInstruction;
		return this;
	}

	public StackFrame push(final Object val) {
		opTop++;
		stack[opTop] = val;
		return this;
	}

	public Object pop() {
		try {
			return stack[opTop];
		} finally {
			opTop--;
		}
	}

	public InstructionNode instruction() {
		return instruction;
	}

	public StackFrame loadConst(final Object val) {
		push(val);
		return this;
	}

	public StackFrame pushAll(final Object[] args) {
		System.arraycopy(args, 0, stack, opTop + 1, args.length);
		opTop += args.length;
		return this;
	}

	public Object local(final int var) {
		return stack[vars + var];
	}

   public void local(final int var, final Object val) {
      stack[vars + var] = val;
   }

	public StackFrame setLocals(final Object[] args) {
		System.arraycopy(args, 0, stack, vars, args.length);
		return this;
	}

	public Object[] pop(final int argCount) {
		final Object[] result = peek(argCount);
		opTop -= argCount;
		return result;
	}

	public Object[] peek(final int argCount) {
		final Object[] result = new Object[argCount];
		System.arraycopy(stack, opTop + 1 - argCount, result, 0, argCount);
		return result;
	}

	public Object peek() {
		return stack[opTop];
	}

	public StackFrame snapshot() {
		return new StackFrame(instruction, copyOf(stack, stack.length), opBot, opTop);
	}

	@Override
	public String toString() {
		final StringBuilder locals = new StringBuilder();
		final StringBuilder operands = new StringBuilder();
		formatStack(locals, vars, opBot);
		formatStack(operands, opBot + 1, opTop);

		return String.format("%s [%s][%s]", instruction, locals, operands);
	}

   private void formatStack(final StringBuilder stackString, final int start, final int end) {
      String separator = "";
      for (int i = start; i <= end; i++) {
			stackString.append(separator);
			stackString.append(stack[i]);
			separator = ", ";
		}
   }
}
