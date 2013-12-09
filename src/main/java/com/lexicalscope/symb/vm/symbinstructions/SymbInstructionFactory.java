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
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
		return SBranchInstruction.geInstruction(feasibilityChecker);
	}

	@Override
	public NullaryOperator iconst(final int val) {
		return new SIConstOperator(val);
	}

   @Override
   public Snapshotable<?> initialMeta() {
      return new Pc();
   }
}
