package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;

public class LAndOp implements Binary2Operator {
   @Override public Object eval(final Object left, final Object right) {
      return (long) left & (long) right;
   }
}
