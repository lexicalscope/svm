package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class IAndOp implements BinaryOperator {
   @Override public Object eval(final Object left, final Object right) {
      return (int) left & (int) right;
   }
}
