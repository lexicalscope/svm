package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;


public class Ificmple implements ICmpOp {
   @Override public String toString() {
      return "IF_ICMPLE";
   }

   @Override public Boolean cmp(final int value1, final int value2) {
      return value1 <= value2;
   }
}
