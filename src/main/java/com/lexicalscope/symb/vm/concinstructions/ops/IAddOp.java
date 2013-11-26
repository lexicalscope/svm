package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;

public class IAddOp implements BinaryOperator {
	@Override
	public Object eval(Object left, Object right) {
		return (int) left + (int) right;
	}

	@Override
	public String toString() {
		return "IADD";
	}
}
