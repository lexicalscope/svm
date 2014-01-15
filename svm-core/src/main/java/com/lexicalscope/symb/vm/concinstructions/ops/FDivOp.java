package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class FDivOp implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (float) left / (float) right;
   }

   @Override public String toString() {
      return "FDIV";
   }
}
