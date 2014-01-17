package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class I2FOp implements Vop {
   @Override public void eval(final Context ctx) {
      ctx.push((float)(int)ctx.pop());
   }

   @Override public String toString() {
      return "I2F";
   }
}
