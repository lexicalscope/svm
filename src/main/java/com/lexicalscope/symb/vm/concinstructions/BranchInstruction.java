package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
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
	public void eval(final SClassLoader cl, final Vm vm, final State state, final InstructionNode instruction) {
		state.op(new BranchOp(instruction, branchPredicate));
	}

	@Override
	public String toString() {
		return String.format("IFGE oc:%s", jumpInsnNode.getNext().getOpcode());
	}
}