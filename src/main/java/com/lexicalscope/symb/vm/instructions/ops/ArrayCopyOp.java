package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class ArrayCopyOp implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final int length = (int) stackFrame.pop();
      final int destPos = (int) stackFrame.pop();
      final Object dest = stackFrame.pop();
      final int  srcPos = (int) stackFrame.pop();
      final Object src = stackFrame.pop();

      for (int i = 0; i < length; i++) {
         // TODO[tim]: check bounds
         final Object val = heap.get(src, ARRAY_PREAMBLE + srcPos + i);
         heap.put(dest, ARRAY_PREAMBLE + destPos + i, val);
      }
   }

   @Override public String toString() {
      return "ARRAYCOPY";
   }
}
