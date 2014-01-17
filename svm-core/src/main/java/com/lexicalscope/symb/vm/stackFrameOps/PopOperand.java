package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;

public final class PopOperand implements Op<Object> {
	@Override public Object eval(final State ctx) {
	   return ctx.pop();
	}
}