package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class Binary2Op implements Vop {
   private final Binary2Operator operator;

   public Binary2Op(final Binary2Operator operator) {
      this.operator = operator;
   }

   @Override public void eval(final State ctx) {
      final Object right = ctx.popDoubleWord();
      final Object left = ctx.popDoubleWord();

      ctx.pushDoubleWord(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
