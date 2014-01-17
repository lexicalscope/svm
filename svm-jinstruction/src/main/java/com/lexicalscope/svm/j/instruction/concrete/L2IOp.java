package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class L2IOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push((int)(long)ctx.popDoubleWord());
   }

   @Override public String toString() {
      return "L2I";
   }
}
