package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class IAddOp implements StackFrameOp {
   @Override
   public void eval(final StackFrame operands) {
      final int a = (int) operands.popOperand();
      final int b = (int) operands.popOperand();
      operands.pushOperand(a + b);
   }

   @Override
   public String toString() {
      return "IADD";
   }
}
