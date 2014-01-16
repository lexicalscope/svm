package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.concinstructions.ops.BranchOp;

public final class BranchInstruction implements Instruction {
   private final BranchPredicate branchPredicate;

   public BranchInstruction(final BranchPredicate branchPredicate) {
      this.branchPredicate = branchPredicate;
   }

   @Override
   public void eval(final Vm<State> vm, final State state, final InstructionNode instruction) {
      state.op(new BranchOp(instruction, branchPredicate));
   }

   @Override
   public String toString() {
      return branchPredicate.toString();
   }
}