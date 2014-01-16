package com.lexicalscope.symb.vm.stackFrameOps;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;

public final class PopOperand implements Op<Object> {
	@Override
	public Object eval(Vm<State> vm, Statics statics, Heap heap, Stack stack, final StackFrame stackFrame) {
		return stackFrame.pop();
	}
}