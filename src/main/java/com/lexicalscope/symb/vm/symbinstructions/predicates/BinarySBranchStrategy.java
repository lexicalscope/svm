package com.lexicalscope.symb.vm.symbinstructions.predicates;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class BinarySBranchStrategy implements SBranchStrategy {
   private final BinarySBranchOp op;

   public BinarySBranchStrategy(final BinarySBranchOp op) {
      this.op = op;
   }

   @Override public ISymbol branchPredicateSymbol(final State state) {
      // TODO[tim]: efficiency of stack operations
      final Object value2 = state.op(popOperand());
      final Object value1 = state.op(popOperand());
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
}
