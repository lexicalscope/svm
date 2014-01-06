package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;

public interface SBranchStrategy {
   GeSymbol branchPredicateSymbol(State state);
}
