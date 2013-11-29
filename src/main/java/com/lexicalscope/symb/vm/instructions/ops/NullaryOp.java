package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class NullaryOp implements StackFrameVop {
	private final NullaryOperator operator;

	public NullaryOp(final NullaryOperator operator) {
		this.operator = operator;
	}

	@Override
	public void eval(final StackFrame stackFrame) {
		stackFrame.push(operator.eval());
	}
	
	@Override
	public String toString() {
		return operator.toString();
	}
}
