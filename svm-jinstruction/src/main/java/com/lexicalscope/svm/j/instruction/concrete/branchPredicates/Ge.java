package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class Ge implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return (int) ctx.pop() >= 0;
   }

   @Override public String toString() {
      return "GE";
   }
}
