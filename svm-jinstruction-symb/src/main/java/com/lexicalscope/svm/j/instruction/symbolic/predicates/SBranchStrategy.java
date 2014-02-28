package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.vm.j.JState;

public interface SBranchStrategy {
   BoolSymbol evaluateBranchConditonAsSymbol(JState ctx);
}
