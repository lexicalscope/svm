package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpNeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class ICmpNeStrategy implements BinarySBranchOp {
   @Override public String toString() {
      return "ICMPNE";
   }

   @Override public ISymbol conditionSymbol(final ISymbol value1, final ISymbol value2) {
      return new ICmpNeSymbol(value1, value2);
   }

   @Override public ISymbol conditionSymbol(final Integer value1, final Integer value2) {
      return pConditionSymbol(value1, value2);
   }

   private ISymbol pConditionSymbol(final int value1, final int value2) {
      if (value1 != value2) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }
}