package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;

public class IushrOperator implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (int)left >>> ((int)right & 0x3f);
   }

   @Override public String toString() {
      return "LUSHR";
   }
}
