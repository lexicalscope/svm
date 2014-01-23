package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class Store implements Vop {
   private final int var;

   public Store(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("STORE %d", var);
   }

   @Override public void eval(final State ctx) {
      ctx.local(var, ctx.pop());
   }
}
