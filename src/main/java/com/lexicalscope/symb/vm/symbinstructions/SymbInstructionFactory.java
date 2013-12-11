package com.lexicalscope.symb.vm.symbinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIConstOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SISubOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

/**
 * @author tim
 */
public class SymbInstructionFactory implements InstructionFactory {
	final FeasibilityChecker feasibilityChecker = new FeasibilityChecker();
   private int symbol = -1;

	@Override
	public BinaryOperator iaddOperation() {
		return new SIAddOperator();
	}

	@Override
	public BinaryOperator imulOperation() {
	   return new SIMulOperator();
	}

   @Override
   public BinaryOperator isubOperation() {
      return new SISubOperator();
   }

	public Symbol symbol() {
		return new ISymbol(++symbol);
	}

	@Override
	public Instruction branchIfGe(final JumpInsnNode jumpInsnNode) {
		return SBranchInstruction.geInstruction(feasibilityChecker);
	}

   @Override public Instruction branchIfNe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchGoto(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

	@Override
	public NullaryOperator iconst(final int val) {
		return new SIConstOperator(val);
	}

	@Override public NullaryOperator lconst(final long val) {
	   throw new UnsupportedOperationException("not implemented yet");
	}

	@Override public NullaryOperator stringPoolLoad(final String constVal) {
	   throw new UnsupportedOperationException("unable to handle symbolic strings yet");
	}

   @Override
   public Snapshotable<?> initialMeta() {
      return new Pc();
   }

   @Override public Instruction branchIfNonNull(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("unable to handle symbolic object yet");
   }
}
