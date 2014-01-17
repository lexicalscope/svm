package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class DoubleToRawLongBits implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.pushDoubleWord(Double.doubleToRawLongBits((double) ctx.popDoubleWord()));
   }

   @Override public String toString() {
      return "DoubleToRawLongBits";
   }
}
