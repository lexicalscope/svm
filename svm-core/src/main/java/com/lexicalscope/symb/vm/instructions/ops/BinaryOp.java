package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class BinaryOp implements Vop {
   private final BinaryOperator operator;

   public BinaryOp(final BinaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(final State ctx) {
      final Object right = ctx.pop();
      final Object left = ctx.pop();

      ctx.push(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
