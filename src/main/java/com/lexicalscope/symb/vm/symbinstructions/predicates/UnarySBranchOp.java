package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface UnarySBranchOp {
   GeSymbol conditionSymbol(ISymbol operand);
}