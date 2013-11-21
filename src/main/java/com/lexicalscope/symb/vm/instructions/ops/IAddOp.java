package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.MutableOperands;

public class IAddOp implements OperandsOp {
   @Override
   public void eval(final MutableOperands operands) {
      final int a = (int) operands.pop();
      final int b = (int) operands.pop();
      operands.push(a + b);
   }
}
