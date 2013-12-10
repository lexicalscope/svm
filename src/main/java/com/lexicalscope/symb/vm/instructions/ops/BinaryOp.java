package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.StackFrame;

public class BinaryOp implements Vop {
	private final BinaryOperator operator;

	public BinaryOp(final BinaryOperator operator) {
		this.operator = operator;
	}

	@Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
	   final Object right = stackFrame.pop();
	   final Object left = stackFrame.pop();

	   stackFrame.push(operator.eval(left, right));
	}

	@Override
	public String toString() {
		return operator.toString();
	}
}
