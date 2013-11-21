package com.lexicalscope.symb.vm;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;



public class StackFrame {
   private final Instruction instruction;
   private final List<Object> locals;
   private final Deque<Object> operands;

   public StackFrame(final Instruction instruction, final List<Object> locals, final Deque<Object> operands) {
      this.instruction = instruction;
      this.locals = locals;
      this.operands = operands;
   }

   public StackFrame advance(final Instruction nextInstruction) {
      return new StackFrame(nextInstruction, locals, operands);
   }

   public Instruction instruction() {
      return instruction;
   }

   public StackFrame load(final int offset) {
      final Deque<Object> operandsCopy = operandsCopy();
      operandsCopy.push(locals.get(offset));
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public StackFrame loadConst(final int i) {
      final Deque<Object> operandsCopy = operandsCopy();
      operandsCopy.push(i);
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public StackFrame pushOperands(final Object[] args) {
      final Deque<Object> operandsCopy = operandsCopy();
      for (final Object arg : args) {
         operandsCopy.push(arg);
      }
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public StackFrame setLocals(final Object[] args) {
      return new StackFrame(instruction, newArrayList(args), operands);
   }

   public StackFrame popOperands(final int count) {
      final Deque<Object> operandsCopy = operandsCopy();
      for (int i = 0; i < count; i++) {
         operandsCopy.pop();
      }
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public Object[] peekOperands(final int argCount) {
      final Iterator<Object> it = operands.iterator();
      final Object[] result = new Object[argCount];
      for (int i = argCount - 1; i >= 0; i--) {
         result[i] = it.next();
      }
      return result;
   }

   public Object peekOperand() {
      return operands.peek();
   }

   public StackFrame popOperand() {
      final Deque<Object> operandsCopy = operandsCopy();
      operandsCopy.pop();
      return new StackFrame(instruction, locals, operandsCopy);
   }

   private Deque<Object> operandsCopy() { return new ArrayDeque<>(operands); }

   public static StackFrame initial(final Instruction instruction) {
      return new StackFrame(instruction, new ArrayList<>(), new ArrayDeque<>());
   }

   @Override
   public boolean equals(final Object obj) {
      if(obj != null && obj.getClass().equals(this.getClass()))
      {
         final StackFrame that = (StackFrame) obj;
         return instruction.equals(that.instruction);
      }
      return false;
   }

   @Override
   public int hashCode() {
      return instruction.hashCode();
   }

   private static Joiner commaJoiner = Joiner.on(", ");

   @Override
   public String toString() {
      return String.format("%s [%s][%s]", instruction, commaJoiner.join(locals), commaJoiner.join(operands));
   }
}
