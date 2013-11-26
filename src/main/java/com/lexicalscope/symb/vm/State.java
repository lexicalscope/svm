package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class State {
   private final Stack stack;
   private final Heap heap;

   public State(final Stack stack, final Heap heap) {
      this.stack = stack;
      this.heap = heap;
   }

   public Instruction instruction() {
      return stack.instruction();
   }

   public State discardTop(final int i) {
      return new State(stack.discardTop(i), heap);
   }

   public State push(final Instruction returnTo, final SMethod method, final int argCount) {
      stack.push(returnTo, method, argCount);
      return this;
   }

   public Stack stack() {
      return stack;
   }

   public <T> T op(final StackFrameOp<T> op) {
	   return stack.op(op);
   }

   @Override
   public String toString() {
      return String.format("stack:<%s>, heap:<%s>", stack, heap);
   }

   public void advance(final Vm vm) {
      instruction().eval(vm, this);
   }
}
