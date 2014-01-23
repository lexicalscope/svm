package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public interface UnarySBranchOp {
   BoolSymbol conditionSymbol(Integer value);
   BoolSymbol conditionSymbol(ISymbol operand);
}