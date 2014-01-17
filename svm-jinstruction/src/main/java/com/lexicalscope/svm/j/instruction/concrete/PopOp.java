package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class PopOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pop();
   }

   @Override public String toString() {
      return "POP";
   }
}
