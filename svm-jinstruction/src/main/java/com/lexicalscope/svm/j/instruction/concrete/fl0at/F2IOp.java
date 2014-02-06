package com.lexicalscope.svm.j.instruction.concrete.fl0at;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class F2IOp implements Vop {
   @Override public void eval(final State ctx) {
      ctx.push((int)(float)ctx.pop());
   }

   @Override public String toString() {
      return "F2I";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.f2i();
   }
}
