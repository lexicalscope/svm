package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class ACmpEq implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      final Object value2 = ctx.pop();
      final Object value1 = ctx.pop();

      return value1.equals(value2);
   }

   @Override public String toString() {
      return "IF_ACMPEQ";
   }
}
