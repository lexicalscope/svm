package com.lexicalscope.svm.j.instruction.concrete.d0uble;

import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;


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
