package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class DupOp implements Vop {
   @Override
   public String toString() {
      return "DUP";
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.peek());
   }
}
