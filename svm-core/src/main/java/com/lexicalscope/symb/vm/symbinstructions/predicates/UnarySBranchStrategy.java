package com.lexicalscope.symb.vm.symbinstructions.predicates;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class UnarySBranchStrategy implements SBranchStrategy {
   private final UnarySBranchOp op;

   public UnarySBranchStrategy(final UnarySBranchOp op) {
      this.op = op;
   }

   @Override public BoolSymbol branchPredicateSymbol(final State state) {
      final Object value = state.op(popOperand(), null);
      if(value instanceof Integer) {
         return op.conditionSymbol((Integer) value);
      } else {
         return op.conditionSymbol((ISymbol) value);
      }
   }
}
