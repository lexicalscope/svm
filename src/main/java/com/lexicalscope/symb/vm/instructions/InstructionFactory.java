package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public interface InstructionFactory {
	BinaryOperator iaddOperation();
	BinaryOperator imulOperation();
	BinaryOperator isubOperation();

	NullaryOperator iconst(int val);

	InstructionTransform branchIfge(JumpInsnNode jumpInsnNode);

	Snapshotable<?> initialMeta();
}
