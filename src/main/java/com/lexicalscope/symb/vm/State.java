package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.instructions.InvokeStatic;
import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;
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

   public State discardTop() {
      return discardTop(0);
   }

   public State discardTop(final int i) {
      return new State(stack.discardTop(i), heap);
   }

   public State push(final Instruction returnTo, final Instruction entry, final int argCount) {
      return new State(stack.push(returnTo, entry, argCount), heap);
   }

   public State advance(final Instruction instruction) {
      return new State(stack.advance(instruction), heap);
   }

   public State loadConst(final int i) {
      return new State(stack.loadConst(i), heap);
   }

   public Object peekOperand() {
      return stack.peekOperand();
   }

   public State popOperand() {
      return new State(stack.popOperand(), heap);
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

   public State op(final Instruction nextInstruction, final OperandsOp op) {
      return new State(stack.op(nextInstruction, op), heap);
   }

   public State op(final Instruction nextInstruction, final StackFrameOp op) {
      return new State(stack.op(nextInstruction, op), heap);
   }

   @Override
   public String toString() {
      return String.format("stack:<%s>, heap:<%s>", stack, heap);
   }
}
