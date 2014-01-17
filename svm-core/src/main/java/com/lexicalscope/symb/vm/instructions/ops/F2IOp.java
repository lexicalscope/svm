package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class F2IOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push((int)(float)ctx.pop());
   }

   @Override public String toString() {
      return "F2I";
   }
}
