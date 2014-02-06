package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class ArrayCopyOp implements Vop {
   @Override public void eval(final State ctx) {
      final int length = (int) ctx.pop();
      final int destPos = (int) ctx.pop();
      final Object dest = ctx.pop();
      final int  srcPos = (int) ctx.pop();
      final Object src = ctx.pop();

      for (int i = 0; i < length; i++) {
         // TODO[tim]: check bounds
         final Object val = ctx.get(src, NewArrayOp.ARRAY_PREAMBLE + srcPos + i);
         ctx.put(dest, NewArrayOp.ARRAY_PREAMBLE + destPos + i, val);
      }
   }

   @Override public String toString() {
      return "ARRAYCOPY";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.arraycopy();
   }
}
