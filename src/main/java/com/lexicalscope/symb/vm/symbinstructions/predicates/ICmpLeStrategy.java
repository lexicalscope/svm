package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpLeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class ICmpLeStrategy implements BinarySBranchOp {
   @Override public String toString() {
      return "ICMPLE";
   }

   @Override public BoolSymbol conditionSymbol(final ISymbol value1, final ISymbol value2) {
      return new ICmpLeSymbol(value1, value2);
   }

   @Override public BoolSymbol conditionSymbol(final Integer value1, final Integer value2) {
      if (value1 <= value2) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }
}