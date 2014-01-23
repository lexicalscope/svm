package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class FloatToRawIntBits implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push(Float.floatToRawIntBits((float) ctx.pop()));
   }

   @Override public String toString() {
      return "FloatToRawIntBits";
   }
}
