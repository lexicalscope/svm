package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public interface InstructionFactory {
	BinaryOperator iaddOperation();
	BinaryOperator imulOperation();
	
	NullaryOperator iconst_m1();

	Instruction branchIfge(JumpInsnNode jumpInsnNode);

}
