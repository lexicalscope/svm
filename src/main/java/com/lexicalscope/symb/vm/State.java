package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;
import com.lexicalscope.symb.vm.instructions.ops.StackVop;

public class State {
	private final Stack stack;
	private final Heap heap;

	public State(final Stack stack, final Heap heap) {
		this.stack = stack;
		this.heap = heap;
	}

	public <T> T op(final StackFrameOp<T> op) {
		return stack.query(op);
	}

	public <T> T op(final StackOp<T> stackOp) {
		return stackOp.eval(stack);
	}

	public State op(final StackFrameVop op) {
		stack.query(new StackFrameOp<Void>() {
			@Override
			public Void eval(final StackFrame stackFrame) {
				op.eval(stackFrame);
				return null;
			}
		});
		return this;
	}

	public State op(final StackVop op) {
		op(new StackOp<Void>() {
			@Override
			public Void eval(final Stack stack) {
				op.eval(stack);
				return null;
			}
		});
		return this;
	}

	public void advance(final SClassLoader cl, final Vm vm) {
		stack.instruction().eval(cl, vm, this);
	}

	public State[] fork(){
		return new State[]{this.snapshot(), this.snapshot()};
	}

	private State snapshot() {
		return new State(stack.snapshot(), heap.snapshot());
	}

	@Override
	public String toString() {
		return String.format("stack:<%s>, heap:<%s>", stack, heap);
	}
}
