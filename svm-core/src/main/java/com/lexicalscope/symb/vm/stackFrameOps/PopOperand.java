package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Op;

public final class PopOperand implements Op<Object> {
	@Override public Object eval(final Context ctx) {
	   return ctx.pop();
	}
}