package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public final class BranchInstruction implements Vop {
   private final BranchPredicate branchPredicate;

   public BranchInstruction(final BranchPredicate branchPredicate) {
      this.branchPredicate = branchPredicate;
   }

   @Override
   public String toString() {
      return branchPredicate.toString();
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.advanceTo(branchPredicate.eval(ctx) ? ctx.instructionJmpTarget() : ctx.instructionNext());
   }
}