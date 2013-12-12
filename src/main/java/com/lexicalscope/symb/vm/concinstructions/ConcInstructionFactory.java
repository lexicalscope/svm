package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.FConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.IMulOp;
import com.lexicalscope.symb.vm.concinstructions.ops.ISubOp;
import com.lexicalscope.symb.vm.concinstructions.ops.LConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.StringPoolLoadOperator;
import com.lexicalscope.symb.vm.concinstructions.predicates.Eq;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ge;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpEq;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpGe;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpLe;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpLt;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpNe;
import com.lexicalscope.symb.vm.concinstructions.predicates.Lt;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ne;
import com.lexicalscope.symb.vm.concinstructions.predicates.NonNull;
import com.lexicalscope.symb.vm.concinstructions.predicates.Unconditional;
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
	public Instruction branchIfGe(final JumpInsnNode jumpInsnNode) {
		return branchInstruction(jumpInsnNode, new Ge());
	}

   @Override
   public Instruction branchIfLt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new Lt());
   }

   @Override
   public Instruction branchIfNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new Ne());
   }

   @Override
   public Instruction branchIfEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new Eq());
   }

   @Override public Instruction branchIfICmpEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new ICmpEq());
   }

   @Override public Instruction branchIfICmpNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new ICmpNe());
   }

   @Override public Instruction branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new ICmpLe());
   }

   @Override public Instruction branchIfICmpGe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new ICmpGe());
   }

   @Override public Instruction branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new ICmpLt());
   }

	@Override public Instruction branchIfNonNull(final JumpInsnNode jumpInsnNode) {
	   return branchInstruction(jumpInsnNode, new NonNull());
	}

   @Override public Instruction branchGoto(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(jumpInsnNode, new Unconditional());
   }

   private Instruction branchInstruction(final JumpInsnNode jumpInsnNode, final BranchPredicate branchPredicate) {
      return new BranchInstruction(branchPredicate, jumpInsnNode);
   }

	@Override
	public NullaryOperator iconst(final int val) {
	   return new IConstOperator(val);
	}

   @Override
   public NullaryOperator lconst(final long val) {
      return new LConstOperator(val);
   }

   @Override
   public NullaryOperator fconst(final float val) {
      return new FConstOperator(val);
   }

	@Override public Vop stringPoolLoad(final String val) {
	   return new StringPoolLoadOperator(val);
	}

   @Override
   public Snapshotable<?> initialMeta() {
      return null;
   }
}
