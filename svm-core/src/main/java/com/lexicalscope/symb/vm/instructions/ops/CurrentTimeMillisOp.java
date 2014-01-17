package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class CurrentTimeMillisOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord(System.currentTimeMillis());
   }

   @Override public String toString() {
      return "CURRENT_TIME_MILLIS";
   }
}
