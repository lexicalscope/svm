package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class CurrentThreadOp implements Vop {
   @Override public void eval(final Context ctx) {
      ctx.push(ctx.currentThread());
   }

   @Override public String toString() {
      return "CURRENT_THREAD";
   }
}
