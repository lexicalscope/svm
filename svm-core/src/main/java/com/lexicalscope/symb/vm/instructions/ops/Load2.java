package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Load2 implements Vop {
   private final int var;

   public Load2(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD2 %d", var);
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.pushDoubleWord(stackFrame.local(var));
   }
}
