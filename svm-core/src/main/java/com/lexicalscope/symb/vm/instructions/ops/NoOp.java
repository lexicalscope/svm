package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class NoOp implements Vop {
   @Override public void eval(final Context ctx) {

   }

   @Override public String toString() {
      return "NOOP";
   }
}
