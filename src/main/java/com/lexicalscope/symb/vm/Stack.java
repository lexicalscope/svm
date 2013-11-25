package com.lexicalscope.symb.vm;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Objects.hash;

import java.util.ArrayDeque;
import java.util.Deque;

import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class Stack {
   private final Deque<StackFrame> stack;

   private Stack(final Deque<StackFrame> stack) {
      this.stack = stack;
   }

   public Stack(final Instruction instruction) {
      this(new ArrayDeque<StackFrame>(){{ push(StackFrame.initial(instruction)); }});
   }

   public Stack discardTop() {
      stack.pop();
      return this;
   }

   public Stack discardTop(final int returnCount) {
      pushOperands(stack.pop().peekOperands(returnCount));
      return this;
   }

   public Stack push(final Instruction returnTo, final Instruction entry, final int argCount) {
      final Object[] args = stack.peek().advance(returnTo).popOperands(argCount);
      stack.push(StackFrame.initial(entry).setLocals(args));
      return this;
   }

   private Stack pushOperands(final Object[] operands) {
      stack.peek().pushOperands(operands);
      return this;
   }

   public Stack advance(final Instruction instruction) {
      stack.peek().advance(instruction);
      return this;
   }

   public Stack loadConst(final int i) {
      stack.peek().loadConst(i);
      return this;
   }

   public Object peekOperand() {
      return stack.peek().peekOperand();
   }

   public Stack popOperand() {
      stack.peek().popOperand();
      return this;
   }

   public Instruction instruction() {
      return head().instruction();
   }

   private StackFrame head() {
      return stack.peek();
   }

   public Stack op(final Instruction nextInstruction, final OperandsOp op) {
      stack.peek().op(nextInstruction, op);
      return this;
   }

   public Stack op(final Instruction nextInstruction, final StackFrameOp op) {
      stack.peek().op(nextInstruction, op);
      return this;
   }

   @Override
   public boolean equals(final Object obj) {
      if(obj != null && obj.getClass().equals(this.getClass()))
      {
         final Stack that = (Stack) obj;
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
