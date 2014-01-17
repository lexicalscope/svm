package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.UnaryOperator;



public class INegOp implements UnaryOperator {
   @Override
   public Object eval(final Object val) {
      return -1 * (int) val;
   }

   @Override
   public String toString() {
      return "INEG";
   }
}
