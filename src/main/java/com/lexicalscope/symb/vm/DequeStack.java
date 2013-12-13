package com.lexicalscope.symb.vm;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Objects.hash;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import com.lexicalscope.symb.vm.classloader.SMethod;

public class DequeStack implements Stack {
	private final Deque<StackFrame> stack;
   private Object currentThread;

	private DequeStack(final Deque<StackFrame> stack, final Object currentThread) {
		this.stack = stack;
      this.currentThread = currentThread;
	}

	public DequeStack(final InstructionNode instruction, final int maxLocals,
			final int maxStack) {
		this(new ArrayDeque<StackFrame>() {
			{
				push(new StackFrame(instruction, maxLocals, maxStack));
			}
		}, null);
	}

	@Override
   public Stack popFrame(final int returnCount) {
		pushOperands(stack.pop().pop(returnCount));
		return this;
	}

	@Override
   public Stack pushFrame(final InstructionNode returnTo, final SMethod method, final int argCount) {
		final Object[] args = head().advance(returnTo).pop(argCount);
		stack.push(new StackFrame(method.entry(), method.maxLocals(),
				method.maxStack()).setLocals(args));
		return this;
	}

	private Stack pushOperands(final Object[] operands) {
		head().pushAll(operands);
		return this;
	}

	@Override
   public InstructionNode instruction() {
		return head().instruction();
	}

	private StackFrame head() {
		return stack.peek();
	}

	@Override public <T> T query(final Op<T> op, final Statics statics, final Heap heap) {
		return op.eval(stack.peek(), this, heap, statics);
	}

	@Override public void query(final Vop op, final Statics statics, final Heap heap) {
	   op.eval(stack.peek(), this, heap, statics);
	}

	@Override
   public int size() {
		return stack.size();
	}

	@Override
   public DequeStack snapshot() {
		final ArrayDeque<StackFrame> stackCopy = new ArrayDeque<>(stack.size());
		for (final Iterator<StackFrame> iterator = stack.descendingIterator(); iterator.hasNext();) {
			stackCopy.push(iterator.next().snapshot());
		}
		assert stackCopy.size() == stack.size();
		return new DequeStack(stackCopy, currentThread);
	}

	@Override public void currentThread(final Object currentThread) {
      this.currentThread = currentThread;
	}

   @Override public Object currentThread() {
      assert currentThread != null;
      return currentThread;
   }

	@Override
	public boolean equals(final Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			final DequeStack that = (DequeStack) obj;
			return elementsEqual(this.stack, that.stack);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hash(stack.toArray());
	}

	@Override
	public String toString() {
		return String.format("%s", stack);
	}
}
