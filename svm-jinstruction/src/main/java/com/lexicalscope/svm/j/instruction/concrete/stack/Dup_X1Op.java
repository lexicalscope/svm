package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class Dup_X1Op implements Vop {
   @Override
   public String toString() {
      return "DUP_X1";
   }

   @Override public void eval(final State ctx) {
      final Object value1 = ctx.pop();
      final Object value2 = ctx.pop();
      ctx.push(value1);
      ctx.push(value2);
      ctx.push(value1);
   }
}
