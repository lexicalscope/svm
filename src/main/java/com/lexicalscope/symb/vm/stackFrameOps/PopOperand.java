package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Op;

public final class PopOperand implements Op<Object> {
	@Override
	public Object eval(final StackFrame stackFrame, Heap heap) {
		return stackFrame.pop();
	}
}