package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpGtSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class ICmpGtStrategy implements BinarySBranchOp {
   @Override public String toString() {
      return "ICMPGT";
   }

   @Override public ISymbol conditionSymbol(final ISymbol value1, final ISymbol value2) {
      return new ICmpGtSymbol(value1, value2);
   }

   @Override public ISymbol conditionSymbol(final Integer value1, final Integer value2) {
      if (value1 > value2) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }
}