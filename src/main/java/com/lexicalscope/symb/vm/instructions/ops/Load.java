package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.StackFrame;

public class Load implements Vop {
   private final int var;

   public Load(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD %d", var);
   }

   @Override public void eval(final StackFrame stackFrame, final Heap heap) {
      stackFrame.push(stackFrame.local(var));
   }
}
