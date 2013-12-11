package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class ArrayStoreOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object value = stackFrame.pop();
      final int offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      heap.put(arrayref, offset + ARRAY_PREAMBLE, value);
   }

   @Override public String toString() {
      return "ARRAYSTORE";
   }
}
