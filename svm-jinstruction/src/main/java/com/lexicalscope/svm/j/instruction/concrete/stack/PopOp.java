package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class PopOp implements Vop {
   @Override public void eval(final JState ctx) {
      ctx.pop();
   }

   @Override public String toString() {
      return "POP";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.pop();
   }
}
