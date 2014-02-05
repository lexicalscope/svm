package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;

public class IshrOperator implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (int)left >> ((int)right & 0x1f);
   }

   @Override public String toString() {
      return "ISHL";
   }
}
