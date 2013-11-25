package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class IAddOp implements StackFrameOp<Void> {
   @Override
   public Void eval(final StackFrame operands) {
      operands.pushOperand((int) operands.popOperand() + (int) operands.popOperand());

      return null;
   }

   @Override
   public String toString() {
      return "IADD";
   }
}
