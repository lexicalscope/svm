package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class ArrayCopyOp implements Vop {
   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
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
