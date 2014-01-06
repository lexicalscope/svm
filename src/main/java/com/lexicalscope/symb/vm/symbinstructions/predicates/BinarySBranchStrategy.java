package com.lexicalscope.symb.vm.symbinstructions.predicates;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class BinarySBranchStrategy implements SBranchStrategy {
   private final BinarySBranchOp op;

   public BinarySBranchStrategy(final BinarySBranchOp op) {
      this.op = op;
   }

   @Override public ISymbol branchPredicateSymbol(final State state) {
      // TODO[tim]: efficiency of stack operations
      final ISymbol value2 = (ISymbol) state.op(popOperand());
      final ISymbol value1 = (ISymbol) state.op(popOperand());
      return op.conditionSymbol(value1, value2);
   }
}
