package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Unconditional implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return true;
   }

   @Override public String toString() {
      return "GOTO";
   }
}
