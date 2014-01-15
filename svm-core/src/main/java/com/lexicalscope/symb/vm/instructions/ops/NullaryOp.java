package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class NullaryOp implements Vop {
	private final NullaryOperator operator;

	public NullaryOp(final NullaryOperator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator.toString();
	}

   @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
      stackFrame.push(operator.eval());
   }
}
