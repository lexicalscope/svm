package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.Vop;

public class IincOp implements Vop {
   private final int var;
   private final int incr;

   public IincOp(final int var, final int incr) {
      this.var = var;
      this.incr = incr;
   }

   @Override public void eval(final State ctx) {
      ctx.local(var, (int)ctx.local(var) + incr);
   }

   @Override public String toString() {
      return String.format("IINC %d %d", var, incr);
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.iinc();
   }
}
