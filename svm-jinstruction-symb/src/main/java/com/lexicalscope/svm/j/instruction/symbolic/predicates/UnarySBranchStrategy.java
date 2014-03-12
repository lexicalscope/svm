package com.lexicalscope.svm.j.instruction.symbolic.predicates;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.vm.j.JState;

public class UnarySBranchStrategy implements SBranchStrategy {
   private final UnarySBranchOp op;

   public UnarySBranchStrategy(final UnarySBranchOp op) {
      this.op = op;
   }

   @Override public BoolSymbol evaluateBranchConditonAsSymbol(final JState ctx) {
      final Object value = ctx.pop();
      if(value instanceof Integer) {
         return op.conditionSymbol((Integer) value);
      } else {
         return op.conditionSymbol((ISymbol) value);
      }
   }

   @Override public String toString() {
      return op.toString();
   }
}
