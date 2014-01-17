package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Gt implements BranchPredicate {
   @Override public Boolean eval(final StateImpl ctx) {
      return (int) ctx.pop() > 0;
   }

   @Override public String toString() {
      return "GT";
   }
}
