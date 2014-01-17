package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.svm.j.instruction.concrete.BinaryOperator;

public class IAndOp implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (int) left & (int) right;
   }

   @Override public String toString() {
      return "IAND";
   }
}
