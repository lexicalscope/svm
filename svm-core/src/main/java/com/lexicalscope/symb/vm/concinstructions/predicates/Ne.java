package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Ne implements BranchPredicate {
	@Override
	public Boolean eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
		return (int) stackFrame.pop() != 0;
	}

	@Override public String toString() {
	   return "NE";
	}
}
