package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class I2LOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord((long)(int) ctx.pop());
   }
}
