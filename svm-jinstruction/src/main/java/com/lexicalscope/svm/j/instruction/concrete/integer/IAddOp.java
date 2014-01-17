package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.BinaryOperator;


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
