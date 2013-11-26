package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.AddSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class SIAddOperator implements BinaryOperator {
	@Override
	public Object eval(Object left, Object right) {
		return new AddSymbol((Symbol) left, (Symbol)right);
	}

	@Override
	public String toString() {
		return "S IADD";
	}
}
