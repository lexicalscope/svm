package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class Unconditional implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return true;
   }

   @Override public String toString() {
      return "GOTO";
   }
}
