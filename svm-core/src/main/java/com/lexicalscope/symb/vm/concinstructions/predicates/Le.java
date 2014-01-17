package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Le implements BranchPredicate {
   @Override public Boolean eval(final Context ctx) {
      return (int) ctx.pop() <= 0;
   }

   @Override public String toString() {
      return "LE";
   }
}
