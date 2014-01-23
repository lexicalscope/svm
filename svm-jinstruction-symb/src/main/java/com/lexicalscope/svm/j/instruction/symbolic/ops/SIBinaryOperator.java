package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public interface SIBinaryOperator {
   Integer eval(Integer value1, Integer value2);
   ISymbol eval(ISymbol svalue1, ISymbol svalue2);
}
