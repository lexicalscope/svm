package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class CurrentTimeMillisOp implements Vop {
   @Override public void eval(final JState ctx) {
      ctx.pushDoubleWord(System.currentTimeMillis());
   }

   @Override public String toString() {
      return "CURRENT_TIME_MILLIS";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.nativ3();
   }
}
