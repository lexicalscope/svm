package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Ge implements BranchPredicate {
	@Override
	public Boolean eval(final StackFrame stackFrame, Stack stack, Heap heap, Statics statics) {
		return (int) stackFrame.pop() >= 0;
	}
}
