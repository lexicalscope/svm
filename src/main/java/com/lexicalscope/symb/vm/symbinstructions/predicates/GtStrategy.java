package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.GtSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class GtStrategy implements UnarySBranchOp {
   @Override public ISymbol conditionSymbol(final ISymbol operand) {
      return new GtSymbol(operand);
   }

   @Override public ISymbol conditionSymbol(final Integer value) {
      if(value > 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFGT";
   }
}