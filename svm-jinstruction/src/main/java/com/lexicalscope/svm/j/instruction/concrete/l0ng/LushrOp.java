package com.lexicalscope.svm.j.instruction.concrete.l0ng;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class LushrOp implements Vop {
   @Override public void eval(final State ctx) {
      final int value2 = (int) ctx.pop();
      final long value1 = (long) ctx.popDoubleWord();

      ctx.pushDoubleWord(value1 >>> (value2 & 0x3f));
   }

   @Override public String toString() {
      return "LUSHR";
   }
}
