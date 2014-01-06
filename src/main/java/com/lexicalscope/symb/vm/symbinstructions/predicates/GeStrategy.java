package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class GeStrategy implements UnarySBranchOp {
   @Override public GeSymbol conditionSymbol(final ISymbol operand) {
      return new GeSymbol(operand);
   }

   @Override public String toString() {
      return "IFGE";
   }
}