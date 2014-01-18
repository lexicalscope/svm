package com.lexicalscope.svm.j.instruction.concrete.fl0at;

import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;


public class FDivOp implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (float) left / (float) right;
   }

   @Override public String toString() {
      return "FDIV";
   }
}
