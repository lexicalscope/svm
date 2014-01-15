package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;

public class DConstOperator implements Nullary2Operator {
   private final double val;

   public DConstOperator(final double val) {
      this.val = val;
   }

   @Override
   public Object eval() {
      return val;
   }

   @Override
   public String toString() {
      return "DCONST_" + val;
   }
}
