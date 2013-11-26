package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstM1Operator;
import com.lexicalscope.symb.vm.concinstructions.ops.IMulOp;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ge;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class ConcInstructionFactory implements InstructionFactory {
	@Override public BinaryOperator iaddOperation() {
		return new IAddOp();
	}

	@Override
	public BinaryOperator imulOperation() {
		return new IMulOp();
	}

	@Override
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
		return new BranchInstruction(new Ge(), jumpInsnNode);
	}

	@Override
	public NullaryOperator iconst_m1() {
		return new IConstM1Operator();
	}
}
