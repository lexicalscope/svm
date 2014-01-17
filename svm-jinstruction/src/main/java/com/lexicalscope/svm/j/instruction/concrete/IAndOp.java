package com.lexicalscope.svm.j.instruction.concrete;


public class IAndOp implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (int) left & (int) right;
   }

   @Override public String toString() {
      return "IAND";
   }
}
