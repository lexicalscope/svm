package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.j.State;

public interface SBranchStrategy {
   BoolSymbol branchPredicateSymbol(State ctx);
}
