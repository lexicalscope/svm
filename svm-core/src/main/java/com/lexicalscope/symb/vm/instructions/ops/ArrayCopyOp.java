package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_PREAMBLE;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class ArrayCopyOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      final int length = (int) ctx.pop();
      final int destPos = (int) ctx.pop();
      final Object dest = ctx.pop();
      final int  srcPos = (int) ctx.pop();
      final Object src = ctx.pop();

      for (int i = 0; i < length; i++) {
         // TODO[tim]: check bounds
         final Object val = ctx.get(src, ARRAY_PREAMBLE + srcPos + i);
         ctx.put(dest, ARRAY_PREAMBLE + destPos + i, val);
      }
   }

   @Override public String toString() {
      return "ARRAYCOPY";
   }
}
