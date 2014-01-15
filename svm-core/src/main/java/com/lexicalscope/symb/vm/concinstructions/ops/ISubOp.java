package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class ISubOp implements BinaryOperator {
	@Override
	public Object eval(final Object left, final Object right) {
		return (int) left - (int) right;
	}

	@Override
	public String toString() {
		return "ISUB";
	}
}
