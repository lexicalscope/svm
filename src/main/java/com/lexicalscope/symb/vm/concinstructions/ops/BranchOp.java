package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public final class BranchOp implements StackFrameVop {
	private final BranchPredicate branchPredicate;
   private final Instruction instruction;

	public BranchOp(
	      final Instruction instruction,
			final BranchPredicate branchPredicate) {
      this.instruction = instruction;
		this.branchPredicate = branchPredicate;
	}

	@Override
	public void eval(final StackFrame stackFrame) {
		final Instruction next;
		if(branchPredicate.eval(stackFrame)) {
			next = instruction.jmpTarget();
		} else {
			next = instruction.next();
		}
		stackFrame.advance(next);
	}
}