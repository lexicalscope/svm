package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class Load implements Vop {
   private final int var;

   public Load(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD %d", var);
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.local(var));
   }
}
