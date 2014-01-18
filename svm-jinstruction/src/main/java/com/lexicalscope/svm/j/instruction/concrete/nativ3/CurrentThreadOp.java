package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class CurrentThreadOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push(ctx.currentThread());
   }

   @Override public String toString() {
      return "CURRENT_THREAD";
   }
}
