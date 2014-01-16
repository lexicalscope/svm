package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class IxorOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int value2 = (int) stackFrame.pop();
      final int value1 = (int) stackFrame.pop();

      stackFrame.push(value1 ^ value2);
   }

   @Override public String toString() {
      return "IXOR";
   }
}
