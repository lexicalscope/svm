package com.lexicalscope.svm.j.instruction.concrete;



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
