package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class Store implements Vop {
   private final int var;

   public Store(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("STORE %d", var);
   }

   @Override public void eval(final JState ctx) {
      ctx.local(var, ctx.pop());
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.store(var);
   }
}
