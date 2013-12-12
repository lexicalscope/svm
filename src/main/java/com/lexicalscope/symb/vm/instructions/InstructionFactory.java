package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public interface InstructionFactory {
	BinaryOperator iaddOperation();
	BinaryOperator imulOperation();
	BinaryOperator isubOperation();

	NullaryOperator iconst(int val);
	NullaryOperator lconst(long val);
	NullaryOperator fconst(float val);
	Vop stringPoolLoad(String constVal);

	Instruction branchIfGe(JumpInsnNode jumpInsnNode);
	Instruction branchIfLt(JumpInsnNode jumpInsnNode);
	Instruction branchIfEq(JumpInsnNode jumpInsnNode);
	Instruction branchIfNe(JumpInsnNode jumpInsnNode);
	Instruction branchIfNonNull(JumpInsnNode jumpInsnNode);
	Instruction branchGoto(JumpInsnNode jumpInsnNode);

	Instruction branchIfICmpEq(JumpInsnNode jumpInsnNode);
	Instruction branchIfICmpNe(JumpInsnNode jumpInsnNode);
	Instruction branchIfICmpLe(JumpInsnNode jumpInsnNode);
	Instruction branchIfICmpGe(JumpInsnNode jumpInsnNode);
	Instruction branchIfICmpLt(JumpInsnNode jumpInsnNode);

	Snapshotable<?> initialMeta();
}
