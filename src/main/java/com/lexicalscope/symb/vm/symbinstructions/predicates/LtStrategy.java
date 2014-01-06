package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.LtSymbol;

public final class LtStrategy implements UnarySBranchOp {
   @Override public ISymbol conditionSymbol(final ISymbol operand) {
      return new LtSymbol(operand);
   }

   @Override public String toString() {
      return "IFLT";
   }
}