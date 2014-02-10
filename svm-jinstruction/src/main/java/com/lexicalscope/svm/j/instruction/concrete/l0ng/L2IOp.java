package com.lexicalscope.svm.j.instruction.concrete.l0ng;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.Vop;

public class L2IOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push((int)(long)ctx.popDoubleWord());
   }

   @Override public String toString() {
      return "L2I";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.l2i();
   }
}
