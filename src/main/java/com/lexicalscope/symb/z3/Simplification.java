package com.lexicalscope.symb.z3;

import com.lexicalscope.symb.vm.symbinstructions.Pc;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ICmpEqSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ITerminalSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker.ISimplificationResult;

public class Simplification {
   private final ISimplificationResult result;
   private final ISymbol symbol;
   private final Pc pc;

   public Simplification(
         final ISymbol symbol,
         final Pc pc,
         final ISimplificationResult result) {
      this.symbol = symbol;
      this.pc = pc;
      this.result = result;
   }

   public void eval(final Simplifier simplifier) {
      final ITerminalSymbol resultSymbol = new ITerminalSymbol("__res");
      final SModel model = simplifier.powerSimplify(pc, new ICmpEqSymbol(resultSymbol, symbol));
      final Symbol resultInterp = model.interp(resultSymbol);
      if(resultInterp instanceof IConstSymbol) {
         result.simplifiedToValue(((IConstSymbol) resultInterp).val());
      } else {
         result.simplified((ISymbol) resultInterp);
      }
   }
}
