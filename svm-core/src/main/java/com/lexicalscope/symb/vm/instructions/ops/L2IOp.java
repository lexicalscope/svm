package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class L2IOp implements Vop {
   @Override public void eval(final StateImpl ctx) {
      ctx.push((int)(long)ctx.popDoubleWord());
   }

   @Override public String toString() {
      return "L2I";
   }
}
