package com.lexicalscope.symb.vm.concinstructions.predicates;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;

public class Lt implements BranchPredicate {
	@Override public Boolean eval(final StateImpl ctx) {
	   return (int) ctx.pop() < 0;
	}

	@Override public String toString() {
	   return "LT";
	}
}
