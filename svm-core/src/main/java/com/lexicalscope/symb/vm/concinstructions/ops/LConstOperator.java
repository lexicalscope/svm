package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;

public class LConstOperator implements Nullary2Operator {
   private final long val;

   public LConstOperator(final long val) {
      this.val = val;
   }

   @Override
   public Object eval() {
      return val;
   }

   @Override
   public String toString() {
      return "LCONST_" + val;
   }
}
