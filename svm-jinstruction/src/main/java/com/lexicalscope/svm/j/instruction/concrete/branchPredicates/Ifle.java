package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;

public class Ifle implements BranchPredicate {
   @Override public Boolean eval(final State ctx) {
      return (int) ctx.pop() <= 0;
   }

   @Override public String toString() {
      return "LE";
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.ifle();
   }
}
