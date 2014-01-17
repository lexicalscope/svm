package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Unconditional implements BranchPredicate {
   @Override public Boolean eval(final Context ctx) {
      return true;
   }

   @Override public String toString() {
      return "GOTO";
   }
}
