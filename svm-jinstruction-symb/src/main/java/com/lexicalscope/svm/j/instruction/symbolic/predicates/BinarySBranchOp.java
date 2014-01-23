package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public interface BinarySBranchOp {
   BoolSymbol conditionSymbol(ISymbol value1, ISymbol value2);
   BoolSymbol conditionSymbol(Integer value1, Integer value2);
}