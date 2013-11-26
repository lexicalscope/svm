package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.NullaryOperator;

public class IConstM1Op implements NullaryOperator {
	@Override
	public Object eval() {
		return -1;
	}
	
	@Override
	public String toString() {
		return "ICONST_M1";
	}
}
