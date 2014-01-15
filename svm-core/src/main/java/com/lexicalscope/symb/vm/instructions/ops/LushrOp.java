package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class LushrOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int value2 = (int) stackFrame.pop();
      final long value1 = (long) stackFrame.popDoubleWord();

      stackFrame.pushDoubleWord(value1 >>> (value2 & 0x3f));
   }

   @Override public String toString() {
      return "LUSHR";
   }
}
