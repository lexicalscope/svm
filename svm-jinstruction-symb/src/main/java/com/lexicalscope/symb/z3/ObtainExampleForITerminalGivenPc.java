package com.lexicalscope.symb.z3;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker.ISimplificationResult;

public class ObtainExampleForITerminalGivenPc implements Simplification {
   private final ISimplificationResult result;
   private final ITerminalSymbol symbol;
   private final Pc pc;

   public ObtainExampleForITerminalGivenPc(
         final ITerminalSymbol symbol,
         final Pc pc,
         final ISimplificationResult result) {
      this.symbol = symbol;
      this.pc = pc;
      this.result = result;
   }

   @Override
   public void eval(final Simplifier simplifier) {
      final SModel model = simplifier.powerSimplify(pc);
      final Symbol resultInterp = model.interp(symbol);
      if(resultInterp instanceof IConstSymbol) {
         result.simplifiedToValue(((IConstSymbol) resultInterp).val());
      } else {
         result.simplified((ISymbol) resultInterp);
      }
   }
}
