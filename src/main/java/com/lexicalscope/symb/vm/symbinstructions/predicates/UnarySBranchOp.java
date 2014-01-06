package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface UnarySBranchOp {
   ISymbol conditionSymbol(ISymbol operand);
}