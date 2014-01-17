package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class DupOp implements Vop {
   @Override
   public String toString() {
      return "DUP";
   }

   @Override public void eval(final Context ctx) {
      ctx.push(ctx.peek());
   }
}
