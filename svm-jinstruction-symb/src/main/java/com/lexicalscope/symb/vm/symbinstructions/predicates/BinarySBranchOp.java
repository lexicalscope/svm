package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface BinarySBranchOp {
   BoolSymbol conditionSymbol(ISymbol value1, ISymbol value2);
   BoolSymbol conditionSymbol(Integer value1, Integer value2);
}