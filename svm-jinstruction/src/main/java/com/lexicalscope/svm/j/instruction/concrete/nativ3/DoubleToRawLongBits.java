package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class DoubleToRawLongBits implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord(Double.doubleToRawLongBits((double) ctx.popDoubleWord()));
   }

   @Override public String toString() {
      return "DoubleToRawLongBits";
   }
}
