package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
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

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final InstructionNode next;
      if(branchPredicate.eval(null, statics, heap, stack, stackFrame)) {
         next = instructionNode.jmpTarget();
      } else {
         next = instructionNode.next();
      }
      stackFrame.advance(next);
   }
}