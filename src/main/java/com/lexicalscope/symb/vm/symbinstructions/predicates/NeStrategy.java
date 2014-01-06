package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.NeSymbol;

public final class NeStrategy implements UnarySBranchOp {
   @Override public ISymbol conditionSymbol(final ISymbol operand) {
      return new NeSymbol(operand);
   }

   @Override public String toString() {
      return "IFNE";
   }
}