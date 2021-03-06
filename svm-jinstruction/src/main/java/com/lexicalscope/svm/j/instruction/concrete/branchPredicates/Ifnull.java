package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;

public class Ifnull implements BranchPredicate {
   @Override public Boolean eval(final JState ctx) {
      return ctx.pop().equals(ctx.nullPointer());
   }

   @Override public String toString() {
      return "NULL";
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.ifnull();
   }
}
