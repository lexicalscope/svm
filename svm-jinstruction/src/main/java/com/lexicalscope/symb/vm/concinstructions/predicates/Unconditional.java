package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class Unconditional implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return true;
   }

   @Override public String toString() {
      return "GOTO";
   }
}
