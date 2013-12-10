package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public final class BranchOp implements Vop {
	private final BranchPredicate branchPredicate;
   private final InstructionNode instruction;

	public BranchOp(
	      final InstructionNode instruction,
			final BranchPredicate branchPredicate) {
      this.instruction = instruction;
		this.branchPredicate = branchPredicate;
	}

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final InstructionNode next;
      if(branchPredicate.eval(stackFrame, stack, heap, statics)) {
         next = instruction.jmpTarget();
      } else {
         next = instruction.next();
      }
      stackFrame.advance(next);
   }
}