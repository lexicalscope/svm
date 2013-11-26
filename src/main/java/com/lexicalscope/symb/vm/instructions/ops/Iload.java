package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class Iload implements StackFrameOp<Void> {
   private final int var;

   public Iload(final int var) {
      this.var = var;
   }

   @Override
   public Void eval(final StackFrame operands) {
      operands.push(operands.local(var));
      return null;
   }

   @Override
   public String toString() {
      return String.format("ILOAD %d", var);
   }
}
