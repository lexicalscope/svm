package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public interface InstructionFactory {
	BinaryOperator iaddOperation();
	BinaryOperator imulOperation();
	BinaryOperator isubOperation();

	NullaryOperator iconst(int val);

	Instruction branchIfge(JumpInsnNode jumpInsnNode);

	Snapshotable<?> initialMeta();
}
