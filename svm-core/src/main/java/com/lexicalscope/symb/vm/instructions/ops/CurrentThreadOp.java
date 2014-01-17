package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class CurrentThreadOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.currentThread());
   }

   @Override public String toString() {
      return "CURRENT_THREAD";
   }
}
