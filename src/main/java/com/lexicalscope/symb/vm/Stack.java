package com.lexicalscope.symb.vm;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Objects.hash;

import java.util.ArrayDeque;
import java.util.Deque;

import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class Stack {
   private final Deque<StackFrame> stack;

   private Stack(final Deque<StackFrame> stack) {
      this.stack = stack;
   }

   public Stack(final Instruction instruction, final int maxLocals, final int maxStack) {
      this(new ArrayDeque<StackFrame>(){{ push(new StackFrame(instruction, maxLocals, maxStack)); }});
   }

   public Stack discardTop() {
      stack.pop();
      return this;
   }

   public Stack discardTop(final int returnCount) {
      pushOperands(stack.pop().pop(returnCount));
      return this;
   }

   public Stack push(final Instruction returnTo, final SMethod method, final int argCount) {
      final Object[] args = stack.peek().advance(returnTo).pop(argCount);
      stack.push(new StackFrame(method.entry(), method.maxLocals(), method.maxStack()).setLocals(args));
      return this;
   }

   private Stack pushOperands(final Object[] operands) {
      stack.peek().pushAll(operands);
      return this;
   }

   public Instruction instruction() {
      return head().instruction();
   }

   private StackFrame head() {
      return stack.peek();
   }

   public <T> T op(final StackFrameOp<T> op) {
      return stack.peek().op(op);
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
