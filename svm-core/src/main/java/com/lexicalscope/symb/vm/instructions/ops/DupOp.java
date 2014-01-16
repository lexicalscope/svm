package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class DupOp implements Vop {
   @Override
   public String toString() {
      return "DUP";
   }

   @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
      stackFrame.push(stackFrame.peek());
   }
}
