package com.lexicalscope.svm.z3;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ICmpEqSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.z3.FeasibilityChecker.ISimplificationResult;

public class SimplifyISymbolGivenPc implements Simplification {
   private final ISimplificationResult result;
   private final ISymbol symbol;
   private final Pc pc;

   public SimplifyISymbolGivenPc(
         final ISymbol symbol,
         final Pc pc,
         final ISimplificationResult result) {
      this.symbol = symbol;
      this.pc = pc;
      this.result = result;
   }

   @Override
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
