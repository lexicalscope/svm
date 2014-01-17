package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class ReturnInstruction implements Vop {
   private final int returnCount;

   public ReturnInstruction(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override public void eval(final Context ctx) {
      ctx.popFrame(returnCount);
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }
}
