package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public class IConstM1Operator implements NullaryOperator {
	@Override
	public Object eval() {
		return -1;
	}
	
	@Override
	public String toString() {
		return "ICONST_M1";
	}
}
