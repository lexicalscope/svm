package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class Null implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return ctx.pop().equals(ctx.nullPointer());
   }

   @Override public String toString() {
      return "NULL";
   }
}
