package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface SBranchStrategy {
   ISymbol branchPredicateSymbol(State state);
}
