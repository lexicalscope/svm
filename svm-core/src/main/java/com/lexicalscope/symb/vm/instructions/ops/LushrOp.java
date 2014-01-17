package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class LushrOp implements Vop {
   @Override public void eval(final Context ctx) {
      final int value2 = (int) ctx.pop();
      final long value1 = (long) ctx.popDoubleWord();

      ctx.pushDoubleWord(value1 >>> (value2 & 0x3f));
   }

   @Override public String toString() {
      return "LUSHR";
   }
}
