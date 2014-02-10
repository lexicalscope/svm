package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.Vop;

public class Load implements Vop {
   private final int var;

   public Load(final int var) {
      this.var = var;
   }

   @Override
   public String toString() {
      return String.format("LOAD %d", var);
   }

   @Override public void eval(final State ctx) {
      ctx.push(ctx.local(var));
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.load(var);
   }
}
