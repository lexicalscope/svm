package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.GeSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public final class GeStrategy implements UnarySBranchOp {
   @Override public GeSymbol conditionSymbol(final ISymbol operand) {
      return new GeSymbol(operand);
   }

   @Override public BoolSymbol conditionSymbol(final Integer value) {
      if(value >= 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFGE";
   }
}