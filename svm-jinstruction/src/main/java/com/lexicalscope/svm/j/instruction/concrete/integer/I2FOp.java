package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class I2FOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push((float)(int)ctx.pop());
   }

   @Override public String toString() {
      return "I2F";
   }
}
