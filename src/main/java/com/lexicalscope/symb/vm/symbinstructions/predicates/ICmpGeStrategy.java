package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpGeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class ICmpGeStrategy implements BinarySBranchOp {
   @Override public String toString() {
      return "ICMPGE";
   }

   @Override public ISymbol conditionSymbol(final ISymbol value1, final ISymbol value2) {
      return new ICmpGeSymbol(value1, value2);
   }
}