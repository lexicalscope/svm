package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;

public interface SBranchStrategy {
   BoolSymbol branchPredicateSymbol(Context ctx);
}
