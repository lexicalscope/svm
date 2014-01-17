package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class I2FOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push((float)(int)ctx.pop());
   }

   @Override public String toString() {
      return "I2F";
   }
}
