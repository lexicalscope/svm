package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class NonNull implements BranchPredicate {
   @Override public Boolean eval(final StateImpl ctx) {
      return !ctx.pop().equals(ctx.nullPointer());
   }

   @Override public String toString() {
      return "NONNULL";
   }
}
