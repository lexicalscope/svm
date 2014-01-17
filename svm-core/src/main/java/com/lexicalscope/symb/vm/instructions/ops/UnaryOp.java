package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;

public class UnaryOp implements Vop {
   private final UnaryOperator operator;

   public UnaryOp(final UnaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(final Context ctx) {
      ctx.push(operator.eval(ctx.pop()));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
