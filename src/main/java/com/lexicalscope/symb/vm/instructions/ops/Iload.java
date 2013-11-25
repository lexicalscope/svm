package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class Iload implements StackFrameOp {
   private final int var;

   public Iload(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("ILOAD %d", var);
   }

   @Override
   public void eval(final StackFrame operands) {
      operands.pushOperand(operands.local(var));
   }
}
