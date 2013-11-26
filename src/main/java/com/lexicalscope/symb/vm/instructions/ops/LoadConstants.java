package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.StackFrame;

public final class LoadConstants implements StackFrameVop {
	private final Object[] values;

	LoadConstants(Object ... values) {
		this.values = values;
	}

	@Override
     public void eval(final StackFrame stackFrame) {
    	 for (Object param : values) {
    		 stackFrame.loadConst(param);
		}
     }
}