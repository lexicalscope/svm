package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class F2IOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push((int)(float)ctx.pop());
   }

   @Override public String toString() {
      return "F2I";
   }
}
