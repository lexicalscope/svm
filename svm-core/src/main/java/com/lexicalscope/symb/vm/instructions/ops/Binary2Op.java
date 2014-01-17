package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class Binary2Op implements Vop {
   private final Binary2Operator operator;

   public Binary2Op(final Binary2Operator operator) {
      this.operator = operator;
   }

   @Override public void eval(final StateImpl ctx) {
      final Object right = ctx.popDoubleWord();
      final Object left = ctx.popDoubleWord();

      ctx.pushDoubleWord(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
