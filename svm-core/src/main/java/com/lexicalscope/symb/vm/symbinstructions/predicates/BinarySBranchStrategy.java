package com.lexicalscope.symb.vm.symbinstructions.predicates;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class BinarySBranchStrategy implements SBranchStrategy {
   private final BinarySBranchOp op;

   public BinarySBranchStrategy(final BinarySBranchOp op) {
      this.op = op;
   }

   @Override public BoolSymbol branchPredicateSymbol(final Context ctx) {
      final Object value2 = ctx.pop();
      final Object value1 = ctx.pop();
      if(value1 instanceof Integer && value2 instanceof Integer) {
         return op.conditionSymbol((Integer) value1, (Integer) value2);
      } else {
         ISymbol svalue1;
         ISymbol svalue2;
         if(value1 instanceof Integer) {
            svalue1 = new IConstSymbol((int) value1);
            svalue2 = (ISymbol) value2;
         } else {
            svalue1 = (ISymbol) value1;
            svalue2 = new IConstSymbol((int) value2);
         }
         return op.conditionSymbol(svalue1, svalue2);
      }
   }

   @Override public String toString() {
      return op.toString();
   }
}
