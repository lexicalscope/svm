package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public class BinaryOp implements StackFrameVop {
	private final BinaryOperator operator;

	public BinaryOp(BinaryOperator operator) {
		this.operator = operator;
	}
	
	@Override
	public void eval(final StackFrame operands) {
		Object right = operands.pop();
		Object left = operands.pop();
		
		operands.push(operator.eval(left, right));
	}

	@Override
	public String toString() {
		return operator.toString();
	}
}
