package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.IMulOp;
import com.lexicalscope.symb.vm.concinstructions.ops.ISubOp;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ge;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public class ConcInstructionFactory implements InstructionFactory {
	@Override public BinaryOperator iaddOperation() {
		return new IAddOp();
	}

	@Override
	public BinaryOperator imulOperation() {
		return new IMulOp();
	}

	@Override
	public BinaryOperator isubOperation() {
	   return new ISubOp();
	}

	@Override
	public InstructionTransform branchIfge(final JumpInsnNode jumpInsnNode) {
		return new BranchInstruction(new Ge(), jumpInsnNode);
	}

	@Override
	public NullaryOperator iconst(final int val) {
	   return new IConstOperator(val);
	}

   @Override
   public Snapshotable<?> initialMeta() {
      return null;
   }
}
