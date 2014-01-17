package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Null implements BranchPredicate {
   @Override public Boolean eval(final Context ctx) {
      return ctx.pop().equals(ctx.nullPointer());
   }

   @Override public String toString() {
      return "NULL";
   }
}
