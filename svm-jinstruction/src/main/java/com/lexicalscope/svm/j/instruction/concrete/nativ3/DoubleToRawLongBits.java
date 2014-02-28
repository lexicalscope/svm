package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class DoubleToRawLongBits implements Vop {
   @Override public void eval(final JState ctx) {
      ctx.pushDoubleWord(Double.doubleToRawLongBits((double) ctx.popDoubleWord()));
   }

   @Override public String toString() {
      return "DoubleToRawLongBits";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.nativ3();
   }
}
