package com.lexicalscope.symb.vm;

import static com.google.common.base.Objects.equal;

public class State implements Snapshotable<State> {
	private final Stack stack;
	private final Heap heap;
   private final Snapshotable<?> meta;

	public State(final Stack stack, final Heap heap, final Snapshotable<?> meta) {
		this.stack = stack;
		this.heap = heap;
      this.meta = meta;
	}

	public <T> T op(final Op<T> op) {
		return stack.query(op, heap);
	}

	public State op(final Vop op) {
	   stack.query(op, heap);
	   return this;
	}

	public <T> T op(final StackOp<T> stackOp) {
		return stackOp.eval(stack);
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

	public void advance(final Vm vm) {
		stack.instruction().eval(vm, this);
	}

	public State[] fork(){
		return new State[]{this.snapshot(), this.snapshot()};
	}

	public Object getMeta() {
	   return meta;
	}

	@Override public State snapshot() {
		return new State(stack.snapshot(), heap.snapshot(), meta == null ? null : meta.snapshot());
	}

	@Override
	public String toString() {
		return String.format("stack:<%s>, heap:<%s>, meta:<%s>", stack, heap, meta);
	}

	@Override
	public boolean equals(final Object obj) {
	   if(obj != null && obj.getClass().equals(this.getClass())) {
	      final State that = (State) obj;
	      return equal(that.stack, this.stack) && equal(that.heap, this.heap) && equal(that.heap, this.heap);
	   }
	   return false;
	}

	@Override
	public int hashCode() {
	   return stack.hashCode() ^ heap.hashCode() ^ (meta == null ? 0 : meta.hashCode());
	}

}
