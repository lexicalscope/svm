package com.lexicalscope.svm.j.instruction.concrete.stack;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class Dup_X1Op implements Vop {
   @Override
   public String toString() {
      return "DUP_X1";
   }

   @Override public void eval(final JState ctx) {
      final Object value1 = ctx.pop();
      final Object value2 = ctx.pop();
      ctx.push(value1);
      ctx.push(value2);
      ctx.push(value1);
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.dup_x1();
   }
}
