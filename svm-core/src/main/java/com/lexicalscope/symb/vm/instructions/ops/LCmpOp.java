package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class LCmpOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final long value2 = (long) stackFrame.popDoubleWord();
      final long value1 = (long) stackFrame.popDoubleWord();

      Object result;
      if(value1 > value2) {
         result = 1;
      } else if(value1 < value2) {
         result = -1;
      } else {
         result = 0;
      }
      stackFrame.push(result);
   }

   @Override public String toString() {
      return "LCMP";
   }
}
