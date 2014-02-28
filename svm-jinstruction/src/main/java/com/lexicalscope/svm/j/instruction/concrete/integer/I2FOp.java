package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class I2FOp implements Vop {
   @Override public void eval(final JState ctx) {
      ctx.push((float)(int)ctx.pop());
   }

   @Override public String toString() {
      return "I2F";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.i2f();
   }
}
