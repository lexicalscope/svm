package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.StateImpl;

public final class PopOperand implements Op<Object> {
	@Override public Object eval(final StateImpl ctx) {
	   return ctx.pop();
	}
}