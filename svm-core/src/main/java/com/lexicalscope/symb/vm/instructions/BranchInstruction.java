package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.concinstructions.ops.BranchOp;

public final class BranchInstruction implements Instruction {
   private final BranchPredicate branchPredicate;

   public BranchInstruction(final BranchPredicate branchPredicate) {
      this.branchPredicate = branchPredicate;
   }

   @Override
   public String toString() {
      return branchPredicate.toString();
   }

   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      new BranchOp(instructionNode, branchPredicate).eval(vm, statics, heap, stack, stackFrame, instructionNode);
   }
}