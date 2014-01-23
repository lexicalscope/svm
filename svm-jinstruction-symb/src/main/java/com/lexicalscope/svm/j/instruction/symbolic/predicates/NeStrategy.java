package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.NeSymbol;

public final class NeStrategy implements UnarySBranchOp {
   @Override public BoolSymbol conditionSymbol(final ISymbol operand) {
      return new NeSymbol(operand);
   }

   @Override public BoolSymbol conditionSymbol(final Integer value) {
      if(value == 0) {
         return new TrueSymbol();
      }
      return new FalseSymbol();
   }

   @Override public String toString() {
      return "IFNE";
   }
}