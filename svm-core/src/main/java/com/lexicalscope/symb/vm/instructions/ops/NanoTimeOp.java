package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class NanoTimeOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.pushDoubleWord(System.nanoTime());
   }

   @Override public String toString() {
      return "NANOTIME";
   }
}
