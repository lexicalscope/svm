package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class UnaryOp implements Vop {
   private final UnaryOperator operator;

   public UnaryOp(final UnaryOperator operator) {
      this.operator = operator;
   }

   @Override public void eval(final State ctx) {
      ctx.push(operator.eval(ctx.pop()));
   }

   @Override
   public String toString() {
      return operator.toString();
   }
}
