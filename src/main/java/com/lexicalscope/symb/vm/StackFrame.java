package com.lexicalscope.symb.vm;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;



public class StackFrame {
   private Instruction instruction;
   private List<Object> locals;
   private final Deque<Object> operands;

   public StackFrame(final Instruction instruction, final List<Object> locals, final Deque<Object> operands) {
      this.instruction = instruction;
      this.locals = locals;
      this.operands = operands;
   }

   public <T> T op(final StackFrameOp<T> op) {
      return op.eval(this);
   }

   public StackFrame advance(final Instruction nextInstruction) {
	   instruction = nextInstruction;
      return this;
   }

   public Instruction instruction() {
      return instruction;
   }

   public StackFrame loadConst(final int i) {
      operands.push(i);
      return this;
   }

   public StackFrame pushOperands(final Object[] args) {
      for (final Object arg : args) {
         operands.push(arg);
      }
      return this;
   }

   public Object local(final int var) {
		return locals.get(var);
	}

   public StackFrame setLocals(final Object[] args) {
      locals = newArrayList(args);
      return this;
   }

   public Object[] popOperands(final int argCount) {
      final Deque<Object> operandsCopy = operands;

      final Object[] result = new Object[argCount];
      for (int i = argCount - 1; i >= 0; i--) {
    	  result[i] = operandsCopy.pop();
      }
      return result;
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

   public Object popOperand() {
      return operands.pop();
   }

   public StackFrame pushOperand(final Object operand) {
	  operands.push(operand);
	  return this;
   }

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
