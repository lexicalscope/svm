package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.vm.symbinstructions.symbols.AndSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class SIAndOperator implements SIBinaryOperator {
   @Override
   public String toString() {
      return "S IAND";
   }

   @Override public Integer eval(final Integer value1, final Integer value2) {
      return value1 & value2;
   }

   @Override public ISymbol eval(final ISymbol svalue1, final ISymbol svalue2) {
      return new AndSymbol(svalue1, svalue2);
   }
}
