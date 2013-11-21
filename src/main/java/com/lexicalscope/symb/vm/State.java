package com.lexicalscope.symb.vm;

import org.hamcrest.Matcher;

import com.lexicalscope.symb.vm.instructions.InvokeStatic;
import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;

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

   public State load(final int i) {
      return new State(stack.load(i), heap);
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

   public static Matcher<? super State> normalTerminiation() {
      return null;
   }

   public Stack stack() {
      return stack;
   }

   @Override
   public String toString() {
      return String.format("stack:<%s>, heap:<%s>", stack, heap);
   }

   public State advance(final Instruction instruction, final OperandsOp op) {
      return advance(instruction).stackOp(op);
   }

   private State stackOp(final OperandsOp op) {
      return new State(stack.stackOp(op), heap);
   }
}
