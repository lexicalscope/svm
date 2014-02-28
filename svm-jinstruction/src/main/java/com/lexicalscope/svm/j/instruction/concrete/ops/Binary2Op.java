package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class Binary2Op implements Vop {
   private final Binary2Operator operator;

   public Binary2Op(final Binary2Operator operator) {
      this.operator = operator;
   }

   @Override public void eval(final JState ctx) {
      final Object right = ctx.popDoubleWord();
      final Object left = ctx.popDoubleWord();

      ctx.pushDoubleWord(operator.eval(left, right));
   }

   @Override
   public String toString() {
      return operator.toString();
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.binaryop();
   }
}
