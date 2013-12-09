package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;

public class BinaryOp implements HeapVop {
	private final BinaryOperator operator;

	public BinaryOp(final BinaryOperator operator) {
		this.operator = operator;
	}

	@Override public void eval(final StackFrame stackFrame, final Heap heap) {
	   final Object right = stackFrame.pop();
	   final Object left = stackFrame.pop();

	   stackFrame.push(operator.eval(left, right));
	}

	@Override
	public String toString() {
		return operator.toString();
	}
}
