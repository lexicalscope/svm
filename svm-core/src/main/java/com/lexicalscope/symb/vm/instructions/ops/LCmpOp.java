package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class LCmpOp implements Vop {
   @Override public void eval(final State ctx) {
      final long value2 = (long) ctx.popDoubleWord();
      final long value1 = (long) ctx.popDoubleWord();

      Object result;
      if(value1 > value2) {
         result = 1;
      } else if(value1 < value2) {
         result = -1;
      } else {
         result = 0;
      }
      ctx.push(result);
   }

   @Override public String toString() {
      return "LCMP";
   }
}
