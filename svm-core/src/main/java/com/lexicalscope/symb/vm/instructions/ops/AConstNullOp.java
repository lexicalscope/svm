package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class AConstNullOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.nullPointer());
   }

   @Override public String toString() {
      return "ACONST_NULL";
   }
}
