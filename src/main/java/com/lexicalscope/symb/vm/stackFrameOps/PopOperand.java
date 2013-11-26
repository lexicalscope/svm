package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public final class PopOperand implements StackFrameOp<Object> {
	@Override
	public Object eval(final StackFrame stackFrame) {
		return stackFrame.pop();
	}
}