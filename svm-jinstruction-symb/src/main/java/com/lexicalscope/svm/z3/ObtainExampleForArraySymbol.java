package com.lexicalscope.svm.z3;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IArrayAndLengthSymbols;

public class ObtainExampleForArraySymbol {
   private final IArrayAndLengthSymbols symbol;
   private final BoolSymbol pc;

   public ObtainExampleForArraySymbol(
         final IArrayAndLengthSymbols symbol,
         final BoolSymbol pc) {
      this.symbol = symbol;
      this.pc = pc;
   }

   public int[] eval(final Simplifier simplifier) {
      return simplifier.powerSimplify(pc).funcInterp(symbol);
   }
}
