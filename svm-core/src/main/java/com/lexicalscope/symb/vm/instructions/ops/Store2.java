package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class Store2 implements Vop {
   private final int var;

   public Store2(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("STORE2 %d", var);
   }

   @Override public void eval(final Context ctx) {
      ctx.local(var, ctx.popDoubleWord());
   }
}
