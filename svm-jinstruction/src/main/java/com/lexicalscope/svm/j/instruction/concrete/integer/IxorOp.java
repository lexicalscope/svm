package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class IxorOp implements Vop {
   @Override public void eval(final State ctx) {
      final int value2 = (int) ctx.pop();
      final int value1 = (int) ctx.pop();

      ctx.push(value1 ^ value2);
   }

   @Override public String toString() {
      return "IXOR";
   }
}
