package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public interface SIUnaryOperator {
   Integer eval(Integer value);
   ISymbol eval(ISymbol svalue);
}
