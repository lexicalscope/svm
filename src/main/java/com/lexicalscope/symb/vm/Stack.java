package com.lexicalscope.symb.vm;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Objects.hash;

import java.util.ArrayDeque;
import java.util.Deque;

import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class Stack {
   private final Deque<StackFrame> stack;

   public Stack(final Deque<StackFrame> stack) {
      this.stack = stack;
   }

   public Stack(final Instruction instruction) {
      this(new ArrayDeque<StackFrame>(){{ push(StackFrame.initial(instruction)); }});
   }

   public Stack discardTop() {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.pop();
      return new Stack(nextStack);
   }

   public Stack discardTop(final int returnCount) {
      final Deque<StackFrame> nextStack = copyStack();
      final StackFrame top = nextStack.pop();
      return new Stack(nextStack).pushOperands(top.peekOperands(returnCount));
   }

   public Stack push(final Instruction returnTo, final Instruction entry, final int argCount) {
      final Deque<StackFrame> nextStack = copyStack();

      final StackFrame topFrame = nextStack.pop();
      final Object[] args = topFrame.peekOperands(argCount);
      nextStack.push(topFrame.advance(returnTo).popOperands(argCount));

      nextStack.push(StackFrame.initial(entry).setLocals(args));
      return new Stack(nextStack);
   }

   private Stack pushOperands(final Object[] operands) {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().pushOperands(operands));
      return new Stack(nextStack);
   }

   public Stack advance(final Instruction instruction) {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().advance(instruction));
      return new Stack(nextStack);
   }

   public Stack loadConst(final int i) {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().loadConst(i));
      return new Stack(nextStack);
   }

   public Object peekOperand() {
      return stack.peek().peekOperand();
   }

   public Stack popOperand() {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().popOperand());
      return new Stack(nextStack);
   }

   public Instruction instruction() {
      return head().instruction();
   }

   private ArrayDeque<StackFrame> copyStack() {
      return new ArrayDeque<StackFrame>(stack);
   }

   private StackFrame head() {
      return stack.peek();
   }

   public Stack op(final Instruction nextInstruction, final OperandsOp op) {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().op(nextInstruction, op));
      return new Stack(nextStack);
   }

   public Stack op(final Instruction nextInstruction, final StackFrameOp op) {
      final Deque<StackFrame> nextStack = copyStack();
      nextStack.push(nextStack.pop().op(nextInstruction, op));
      return new Stack(nextStack);
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
