package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class IincOp implements Vop {
   private final int var;
   private final int incr;

   public IincOp(final int var, final int incr) {
      this.var = var;
      this.incr = incr;
   }

   @Override public void eval(final Context ctx) {
      ctx.local(var, (int)ctx.local(var) + incr);
   }

   @Override public String toString() {
      return String.format("IINC %d %d", var, incr);
   }
}
