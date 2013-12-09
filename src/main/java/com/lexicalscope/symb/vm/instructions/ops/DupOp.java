package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;

public class DupOp implements HeapVop {
   @Override
   public String toString() {
      return "DUP";
   }

   @Override public void eval(final StackFrame stackFrame, final Heap heap) {
      stackFrame.push(stackFrame.peek());
   }
}
