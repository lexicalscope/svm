package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface UnarySBranchOp {
   BoolSymbol conditionSymbol(Integer value);
   BoolSymbol conditionSymbol(ISymbol operand);
}