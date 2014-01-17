package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class IorOp implements Vop {
   @Override public void eval(final Context ctx) {
      final int value2 = (int) ctx.pop();
      final int value1 = (int) ctx.pop();

      ctx.push(value1 | value2);
   }

   @Override public String toString() {
      return "IOR";
   }
}
