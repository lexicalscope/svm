package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class Nullary2Op implements Vop {
   private final Nullary2Operator operator;

   public Nullary2Op(final Nullary2Operator operator) {
      this.operator = operator;
   }

   @Override
   public String toString() {
      return operator.toString();
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.pushDoubleWord(operator.eval());
   }
}
