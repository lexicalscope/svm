package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.ops.BranchOp;

final class BranchInstruction implements Instruction {
	private final BranchPredicate branchPredicate;
	private final JumpInsnNode jumpInsnNode;

	BranchInstruction(final BranchPredicate branchPredicate,
			final JumpInsnNode jumpInsnNode) {
		this.branchPredicate = branchPredicate;
		this.jumpInsnNode = jumpInsnNode;
	}

	@Override
	public State eval(final SClassLoader cl, final State state) {
		state.op(new BranchOp(cl, jumpInsnNode, branchPredicate));
		return state;
	}

	@Override
	public String toString() {
		return String.format("IFGE %s", jumpInsnNode.label);
	}
}