package com.lexicalscope.svm.z3;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ITerminalSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.Symbol;
import com.lexicalscope.svm.z3.FeasibilityChecker.ISimplificationResult;

public class ObtainExampleForITerminalGivenPc implements Simplification {
   private final ISimplificationResult result;
   private final ITerminalSymbol symbol;
   private final BoolSymbol pc;

   public ObtainExampleForITerminalGivenPc(
         final ITerminalSymbol symbol,
         final BoolSymbol pc,
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
