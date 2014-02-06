package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class DupOp implements Vop {
   @Override
   public String toString() {
      return "DUP";
   }

   @Override public void eval(final State ctx) {
      ctx.push(ctx.peek());
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.dup();
   }
}
