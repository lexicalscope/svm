package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public interface SIBinaryOperator {
   Integer eval(Integer value1, Integer value2);
   ISymbol eval(ISymbol svalue1, ISymbol svalue2);
}
