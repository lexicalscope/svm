package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class I2LOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.pushDoubleWord((long)(int) ctx.pop());
   }
}
