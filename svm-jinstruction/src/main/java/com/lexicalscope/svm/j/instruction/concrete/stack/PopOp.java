package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class PopOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.pop();
   }

   @Override public String toString() {
      return "POP";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.pop();
   }
}
