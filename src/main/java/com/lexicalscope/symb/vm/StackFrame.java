package com.lexicalscope.symb.vm;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;



public class StackFrame {
   private final Instruction instruction;
   private final List<Object> locals;
   private final Deque<Object> operands;

   public StackFrame(final Instruction instruction, final List<Object> locals, final Deque<Object> operands) {
      this.instruction = instruction;
      this.locals = locals;
      this.operands = operands;
   }

   public StackFrame op(final Instruction nextInstruction, final OperandsOp op) {
      final Deque<Object> operandsCopy = copyOperands();

      op.eval(new MutableOperands(locals, operandsCopy));

      return new StackFrame(nextInstruction, locals, operandsCopy);
   }


   public StackFrame op(final Instruction nextInstruction, final StackFrameOp op) {
      final Deque<Object> operandsCopy = copyOperands();
      final List<Object> localsCopy = copyLocals();

      op.eval(new MutableStackFrame(localsCopy, operandsCopy));

      return new StackFrame(nextInstruction, localsCopy, operandsCopy);
   }

   public StackFrame advance(final Instruction nextInstruction) {
      return new StackFrame(nextInstruction, locals, operands);
   }

   public Instruction instruction() {
      return instruction;
   }

   public StackFrame loadConst(final int i) {
      final Deque<Object> operandsCopy = copyOperands();
      operandsCopy.push(i);
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public StackFrame pushOperands(final Object[] args) {
      final Deque<Object> operandsCopy = copyOperands();
      for (final Object arg : args) {
         operandsCopy.push(arg);
      }
      return new StackFrame(instruction, locals, operandsCopy);
   }

   public StackFrame setLocals(final Object[] args) {
      return new StackFrame(instruction, newArrayList(args), operands);
   }

   public StackFrame popOperands(final int count) {
      final Deque<Object> operandsCopy = copyOperands();
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
      final Deque<Object> operandsCopy = copyOperands();
      operandsCopy.pop();
      return new StackFrame(instruction, locals, operandsCopy);
   }

   private Deque<Object> copyOperands() { return new ArrayDeque<>(operands); }
   private List<Object> copyLocals() { return new ArrayList<>(locals); }

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
