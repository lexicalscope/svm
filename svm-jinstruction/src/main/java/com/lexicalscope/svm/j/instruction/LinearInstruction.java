package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class LinearInstruction implements Vop {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   @Override public void eval(final State ctx) {
      ctx.advanceToNextInstruction();
      op.eval(ctx);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
