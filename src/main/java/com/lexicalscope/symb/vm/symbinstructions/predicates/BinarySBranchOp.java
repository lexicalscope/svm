package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface BinarySBranchOp {
   ISymbol conditionSymbol(ISymbol value1, ISymbol value2);
}