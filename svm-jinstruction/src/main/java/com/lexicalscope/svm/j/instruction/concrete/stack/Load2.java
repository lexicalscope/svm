package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class Load2 implements Vop {
   private final int var;

   public Load2(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD2 %d", var);
   }

   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord(ctx.local(var));
   }
}
