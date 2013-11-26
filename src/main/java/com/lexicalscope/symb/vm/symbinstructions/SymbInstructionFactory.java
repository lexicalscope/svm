package com.lexicalscope.symb.vm.symbinstructions;

import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOp;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class SymbInstructionFactory implements InstructionFactory {
	private int symbol = -1;
	
	@Override
	public BinaryOperator addOperation() {
		return new SIAddOp();
	}

	public Symbol symbol() {
		return new ValueSymbol(++symbol);
	}
}
