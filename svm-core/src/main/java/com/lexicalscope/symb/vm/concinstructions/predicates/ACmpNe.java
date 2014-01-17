package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class ACmpNe implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      final Object value2 = ctx.pop();
      final Object value1 = ctx.pop();

      return !value1.equals(value2);
   }

   @Override public String toString() {
      return "IF_ACMPNE";
   }
}
