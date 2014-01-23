package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class Nullary2Op implements Vop {
   private final Nullary2Operator operator;

   public Nullary2Op(final Nullary2Operator operator) {
      this.operator = operator;
   }

   @Override
   public String toString() {
      return operator.toString();
   }

   @Override public void eval(final State ctx) {
      ctx.pushDoubleWord(operator.eval());
   }
}
