package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class ICmpBranchPredicate implements BranchPredicate {
   private final ICmpOp op;

   public ICmpBranchPredicate(final ICmpOp op) {
      this.op = op;
   }

   @Override public Boolean eval(final StateImpl ctx) {
      final int value2 = (int) ctx.pop();
      final int value1 = (int) ctx.pop();
      return op.cmp(value1, value2);
   }

   @Override public String toString() {
      return op.toString();
   }
}
