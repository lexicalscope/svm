package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Ne implements BranchPredicate {
	@Override public Boolean eval(final State ctx) {
	   return (int) ctx.pop() != 0;
	}

	@Override public String toString() {
	   return "NE";
	}
}
