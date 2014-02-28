package com.lexicalscope.svm.j.instruction.concrete.branch;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public final class BranchInstruction implements Vop {
   private final BranchPredicate branchPredicate;

   public BranchInstruction(final BranchPredicate branchPredicate) {
      this.branchPredicate = branchPredicate;
   }

   @Override
   public String toString() {
      return branchPredicate.toString();
   }

   @Override public void eval(final JState ctx) {
      ctx.advanceTo(branchPredicate.eval(ctx) ? ctx.instructionJmpTarget() : ctx.instructionNext());
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.branch();
   }
}