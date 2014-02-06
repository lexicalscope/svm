package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

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

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.r3turn(returnCount);
   }
}
