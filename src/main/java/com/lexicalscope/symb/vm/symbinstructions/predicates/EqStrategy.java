package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.EqSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class EqStrategy implements UnarySBranchOp {
   @Override public ISymbol conditionSymbol(final ISymbol operand) {
      return new EqSymbol(operand);
   }

   @Override public ISymbol conditionSymbol(final Integer value) {
      if(value == 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFEQ";
   }
}