package com.lexicalscope.symb.vm.symbinstructions.predicates;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class UnarySBranchStrategy implements SBranchStrategy {
   private final UnarySBranchOp op;

   public UnarySBranchStrategy(final UnarySBranchOp op) {
      this.op = op;
   }

   @Override public ISymbol branchPredicateSymbol(final State state) {
      final ISymbol operand = (ISymbol) state.op(popOperand());
      return op.conditionSymbol(operand);
   }
}
