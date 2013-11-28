package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class DupOp implements StackFrameVop {
   @Override
   public void eval(final StackFrame stackFrame) {
      stackFrame.push(stackFrame.peek());
   }

   @Override
   public String toString() {
      return "DUP";
   }
}
