package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.MulSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class SIMulOperator implements BinaryOperator {
	@Override
	public Object eval(final Object left, final Object right) {
		return new MulSymbol((ISymbol) left, (ISymbol)right);
	}

	@Override
	public String toString() {
		return "S IMUL";
	}
}
