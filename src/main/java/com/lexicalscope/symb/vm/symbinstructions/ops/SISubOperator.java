package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.SubSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class SISubOperator implements BinaryOperator {
	@Override
	public Object eval(final Object left, final Object right) {
		return new SubSymbol((Symbol) left, (Symbol)right);
	}

	@Override
	public String toString() {
		return "S ISUB";
	}
}
