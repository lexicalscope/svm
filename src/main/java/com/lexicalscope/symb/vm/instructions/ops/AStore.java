package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class AStore implements StackFrameVop {
   private final int var;

   public AStore(final int var) {
      this.var = var;
   }

   @Override
   public void eval(final StackFrame stackFrame) {
      stackFrame.local(var, stackFrame.pop());
   }

   @Override
   public String toString() {
      return String.format("ASTORE %d", var);
   }
}
