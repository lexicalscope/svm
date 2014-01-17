package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class LinearInstruction implements Vop {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   @Override public void eval(final Context ctx) {
      ctx.advanceToNextInstruction();
      op.eval(ctx);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
