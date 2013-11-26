package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public class IAddOp implements StackFrameOp<Void> {
   @Override
   public Void eval(final StackFrame operands) {
      operands.push((int) operands.pop() + (int) operands.pop());

      return null;
   }

   @Override
   public String toString() {
      return "IADD";
   }
}
