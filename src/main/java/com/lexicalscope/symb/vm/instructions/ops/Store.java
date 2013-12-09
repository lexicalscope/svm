package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.StackFrame;

public class Store implements Vop {
   private final int var;

   public Store(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("ASTORE %d", var);
   }

   @Override public void eval(final StackFrame stackFrame, final Heap heap) {
      stackFrame.local(var, stackFrame.pop());
   }
}
