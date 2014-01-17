package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;


public class ICmpGt implements ICmpOp {
   @Override public String toString() {
      return "ICMPGT";
   }

   @Override public Boolean cmp(final int value1, final int value2) {
      return value1 > value2;
   }
}
