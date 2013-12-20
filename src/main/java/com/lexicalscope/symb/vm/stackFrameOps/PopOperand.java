package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;

public final class PopOperand implements Op<Object> {
	@Override
	public Object eval(final StackFrame stackFrame, Stack stack, Heap heap, Statics statics) {
		return stackFrame.pop();
	}
}