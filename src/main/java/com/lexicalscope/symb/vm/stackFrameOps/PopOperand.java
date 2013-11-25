package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameOp;

public final class PopOperand implements StackFrameOp {
	@Override
	public void eval(StackFrame stackFrame) {
		stackFrame.popOperand();
	}
}