package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.MutableOperands;

public class Iload implements OperandsOp {
   private final int var;

   public Iload(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("ILOAD %d", var);
   }

   @Override
   public void eval(final MutableOperands operands) {
      operands.push(operands.local(var));
   }
}
