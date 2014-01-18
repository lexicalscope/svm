package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;


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
