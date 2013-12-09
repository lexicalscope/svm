package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public final class BranchOp implements StackFrameVop {
	private final BranchPredicate branchPredicate;
   private final InstructionNode instruction;

	public BranchOp(
	      final InstructionNode instruction,
			final BranchPredicate branchPredicate) {
      this.instruction = instruction;
		this.branchPredicate = branchPredicate;
	}

	@Override
	public void eval(final StackFrame stackFrame) {
		final InstructionNode next;
		if(branchPredicate.eval(stackFrame)) {
			next = instruction.jmpTarget();
		} else {
			next = instruction.next();
		}
		stackFrame.advance(next);
	}
}