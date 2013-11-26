package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Ge implements BranchPredicate {
	@Override
	public Boolean eval(final StackFrame stackFrame) {
		return (int) stackFrame.pop() >= 0;
	}
}
