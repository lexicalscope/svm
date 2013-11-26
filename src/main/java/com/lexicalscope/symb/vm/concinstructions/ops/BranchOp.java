package com.lexicalscope.symb.vm.concinstructions.ops;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public final class BranchOp implements StackFrameVop {
	private final SClassLoader cl;
	private final BranchPredicate branchPredicate;
	private final JumpInsnNode jumpInsnNode;

	public BranchOp(final SClassLoader cl, final JumpInsnNode jumpInsnNode,
			final BranchPredicate branchPredicate) {
		this.cl = cl;
		this.branchPredicate = branchPredicate;
		this.jumpInsnNode = jumpInsnNode;
	}

	@Override
	public void eval(final StackFrame stackFrame) {
		final AbstractInsnNode next;
		if(branchPredicate.eval(stackFrame)) {
			next = jumpInsnNode.label.getNext();
		} else {
			next = jumpInsnNode.getNext();
		}
		stackFrame.advance(cl.instructionFor(next));
	}
}