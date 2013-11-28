package com.lexicalscope.symb.vm;

import static java.util.Arrays.copyOf;

import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public final class StackFrame {
	private final Object[] stack;
	private Instruction instruction; // PC
	private int opTop; // pointer to top of operand stack
	private final int vars = 0; // pointer to local variables

	public StackFrame(
			final Instruction instruction,
			final int maxLocals,
			final int maxStack) {
		this(instruction, new Object[maxLocals + maxStack], maxLocals - 1);
	}

	private StackFrame(final Instruction instruction, final Object[] stack, final int opTop) {
		this.instruction = instruction;
		this.stack = stack;
		this.opTop = opTop;
	}

	public <T> T op(final StackFrameOp<T> op) {
		return op.eval(this);
	}

	public StackFrame advance(final Instruction nextInstruction) {
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

	public Instruction instruction() {
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
		return new StackFrame(instruction, copyOf(stack, stack.length), opTop);
	}

	@Override
	public String toString() {
		String separator = "";
		final StringBuilder stackString = new StringBuilder();
		for (int i = vars; i <= opTop; i++) {
			stackString.append(separator);
			stackString.append(stack[i]);
			separator = ", ";
		}

		return String.format("%s [%s]", instruction, stackString);
	}
}
