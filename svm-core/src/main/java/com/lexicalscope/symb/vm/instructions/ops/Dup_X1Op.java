package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class Dup_X1Op implements Vop {
   @Override
   public String toString() {
      return "DUP_X1";
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object value1 = stackFrame.pop();
      final Object value2 = stackFrame.pop();
      stackFrame.push(value1);
      stackFrame.push(value2);
      stackFrame.push(value1);
   }
}
