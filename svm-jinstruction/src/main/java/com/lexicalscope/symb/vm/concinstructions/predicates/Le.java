package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.svm.j.instruction.concrete.BranchPredicate;
import com.lexicalscope.symb.vm.State;

public class Le implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return (int) ctx.pop() <= 0;
   }

   @Override public String toString() {
      return "LE";
   }
}
