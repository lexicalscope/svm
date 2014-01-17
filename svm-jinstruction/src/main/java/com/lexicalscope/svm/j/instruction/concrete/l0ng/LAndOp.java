package com.lexicalscope.svm.j.instruction.concrete.l0ng;

import com.lexicalscope.svm.j.instruction.concrete.Binary2Operator;


public class LAndOp implements Binary2Operator {
   @Override public Object eval(final Object left, final Object right) {
      return (long) left & (long) right;
   }

   @Override public String toString() {
      return "LAND";
   }
}
