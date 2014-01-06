package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.LeSymbol;

public final class LeStrategy implements UnarySBranchOp {
   @Override public ISymbol conditionSymbol(final ISymbol operand) {
      return new LeSymbol(operand);
   }

   @Override public ISymbol conditionSymbol(final Integer value) {
      if(value <= 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFLE";
   }
}