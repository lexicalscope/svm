package com.lexicalscope.svm.j.instruction.concrete.object;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class AConstNullOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push(ctx.nullPointer());
   }

   @Override public String toString() {
      return "ACONST_NULL";
   }
}
