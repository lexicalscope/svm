package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class Store2 implements Vop {
   private final int var;

   public Store2(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("STORE2 %d", var);
   }

   @Override public void eval(final State ctx) {
      ctx.local(var, ctx.popDoubleWord());
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.store(var);
   }
}
