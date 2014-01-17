package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class I2LOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord((long)(int) ctx.pop());
   }
}
