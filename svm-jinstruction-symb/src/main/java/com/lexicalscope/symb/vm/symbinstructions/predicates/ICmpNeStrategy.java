package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpNeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class ICmpNeStrategy implements BinarySBranchOp {
   @Override public String toString() {
      return "ICMPNE";
   }

   @Override public BoolSymbol conditionSymbol(final ISymbol value1, final ISymbol value2) {
      return new ICmpNeSymbol(value1, value2);
   }

   @Override public BoolSymbol conditionSymbol(final Integer value1, final Integer value2) {
      return pConditionSymbol(value1, value2);
   }

   private BoolSymbol pConditionSymbol(final int value1, final int value2) {
      if (value1 != value2) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }
}