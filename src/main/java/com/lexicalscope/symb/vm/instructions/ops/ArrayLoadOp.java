package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class ArrayLoadOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      stackFrame.push(heap.get(arrayref, offset + ARRAY_PREAMBLE));
   }

   @Override public String toString() {
      return "ARRAYLOAD";
   }
}
