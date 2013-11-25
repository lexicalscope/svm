package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.instructions.InvokeStatic;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class State {
   private final Stack stack;
   private final Heap heap;

   private State(final Stack stack, final Heap heap) {
      this.stack = stack;
      this.heap = heap;
   }

   public Instruction instruction() {
      return stack.instruction();
   }

   public State discardTop(final int i) {
      return new State(stack.discardTop(i), heap);
   }

   public State push(final Instruction returnTo, final Instruction entry, final int argCount) {
      return new State(stack.push(returnTo, entry, argCount), heap);
   }

   public Object peekOperand() {
      return stack.peekOperand();
   }

   public static State initial(final String klass) {
      return initial(klass, "main", "([Ljava/lang/String;)V");
   }

   public static State initial(final String klass, final String method, final String desc) {
      return new State(new Stack(new InvokeStatic(klass, method, desc)), new Heap());
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
}
