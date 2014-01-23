package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.EqSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;

public final class EqStrategy implements UnarySBranchOp {
   @Override public BoolSymbol conditionSymbol(final ISymbol operand) {
      return new EqSymbol(operand);
   }

   @Override public BoolSymbol conditionSymbol(final Integer value) {
      if(value == 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFEQ";
   }
}