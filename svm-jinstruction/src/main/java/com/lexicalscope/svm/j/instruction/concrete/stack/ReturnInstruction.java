package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class ReturnInstruction implements Vop {
   private final int returnCount;

   public ReturnInstruction(final int returnCount) {
      this.returnCount = returnCount;
   }

   @Override public void eval(final State ctx) {
      ctx.popFrame(returnCount);
   }

   @Override public String toString() {
      return String.format("RETURN %s", returnCount);
   }
}
