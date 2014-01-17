package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class NanoTimeOp implements Vop {
   @Override public void eval(final Context ctx) {
      ctx.pushDoubleWord(System.nanoTime());
   }

   @Override public String toString() {
      return "NANOTIME";
   }
}
