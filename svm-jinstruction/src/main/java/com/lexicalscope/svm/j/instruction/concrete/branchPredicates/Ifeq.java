package com.lexicalscope.svm.j.instruction.concrete.branchPredicates;

import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.State;

public final class Ifeq implements BranchPredicate {
	@Override public Boolean eval(final State ctx) {
	   return (int) ctx.pop() == 0;
	}

	@Override public String toString() {
	   return "EQ";
	}

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.ifeq();
   }
}
