package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class CurrentTimeMillisOp implements Vop {
   @Override public void eval(final Context ctx) {
      ctx.pushDoubleWord(System.currentTimeMillis());
   }

   @Override public String toString() {
      return "CURRENT_TIME_MILLIS";
   }
}
