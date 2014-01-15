package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class IincOp implements Vop {
   private final int var;
   private final int incr;

   public IincOp(final int var, final int incr) {
      this.var = var;
      this.incr = incr;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.local(var, (int)stackFrame.local(var) + incr);
   }

   @Override public String toString() {
      return String.format("IINC %d %d", var, incr);
   }
}
