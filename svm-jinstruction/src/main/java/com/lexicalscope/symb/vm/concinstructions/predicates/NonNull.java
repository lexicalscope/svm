package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class NonNull implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return !ctx.pop().equals(ctx.nullPointer());
   }

   @Override public String toString() {
      return "NONNULL";
   }
}
