package com.lexicalscope.svm.j.instruction.concrete;


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
