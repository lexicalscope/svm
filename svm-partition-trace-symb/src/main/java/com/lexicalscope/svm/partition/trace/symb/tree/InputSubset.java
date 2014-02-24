package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface InputSubset {
   boolean covers(BoolSymbol pc);
   BoolSymbol pc();
}