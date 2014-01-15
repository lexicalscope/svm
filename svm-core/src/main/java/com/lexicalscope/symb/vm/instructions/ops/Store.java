package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Store implements Vop {
   private final int var;

   public Store(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("STORE %d", var);
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.local(var, stackFrame.pop());
   }
}
