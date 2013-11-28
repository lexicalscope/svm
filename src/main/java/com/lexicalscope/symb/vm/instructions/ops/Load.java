package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class Load implements StackFrameVop {
   private final int var;

   public Load(final int var) {
      this.var = var;
   }

   @Override
   public void eval(final StackFrame operands) {
      operands.push(operands.local(var));
   }

   @Override
   public String toString() {
      return String.format("LOAD %d", var);
   }
}
