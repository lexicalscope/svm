package com.lexicalscope.symb.vm.symbinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOp;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class SymbInstructionFactory implements InstructionFactory {
	private int symbol = -1;
	
	@Override
	public BinaryOperator iaddOperation() {
		return new SIAddOp();
	}
	
	@Override
	public BinaryOperator imulOperation() {
		throw new UnsupportedOperationException();
	}

	public Symbol symbol() {
		return new ValueSymbol(++symbol);
	}

	@Override
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NullaryOperator iconst_m1() {
		throw new UnsupportedOperationException();
	}
}
