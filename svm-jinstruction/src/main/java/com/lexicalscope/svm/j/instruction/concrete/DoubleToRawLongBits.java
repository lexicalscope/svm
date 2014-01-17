package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class DoubleToRawLongBits implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord(Double.doubleToRawLongBits((double) ctx.popDoubleWord()));
   }

   @Override public String toString() {
      return "DoubleToRawLongBits";
   }
}
