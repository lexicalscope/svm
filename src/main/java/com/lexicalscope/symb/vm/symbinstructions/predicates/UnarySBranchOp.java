package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface UnarySBranchOp {
   ISymbol conditionSymbol(Integer value);
   ISymbol conditionSymbol(ISymbol operand);
}