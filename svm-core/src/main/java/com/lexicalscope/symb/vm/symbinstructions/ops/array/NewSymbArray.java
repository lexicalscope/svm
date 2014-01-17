package com.lexicalscope.symb.vm.symbinstructions.ops.array;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.instructions.ops.array.ArrayConstructor;
import com.lexicalscope.symb.vm.instructions.ops.array.InitStrategy;
import com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp;
import com.lexicalscope.symb.vm.instructions.ops.array.NewConcArray;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArrayZeroedSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;
import com.lexicalscope.symb.z3.FeasibilityChecker.ISimplificationResult;

public class NewSymbArray implements ArrayConstructor {
   public static final int ARRAY_SYMBOL_OFFSET = NewArrayOp.ARRAY_PREAMBLE + 0;
   private final FeasibilityChecker feasibilityChecker;

   public NewSymbArray(final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public void newArray(final Context ctx, final InitStrategy initStrategy) {
      final Object top = ctx.pop();
      if(top instanceof ISymbol) {
         feasibilityChecker.simplifyBv32Expr((ISymbol) top, new ISimplificationResult(){
            @Override public void simplifiedToValue(final int arrayLength) {
               newConcreteArray(ctx, initStrategy, arrayLength);
            }

            @Override public void simplified(final ISymbol simplification) {
               newSymbolicArray(ctx, initStrategy, simplification);
            }});
      } else if (top instanceof Integer) {
         newConcreteArray(ctx, initStrategy, (int) top);
      } else {
         throw new UnsupportedOperationException("array length " + top);
      }
   }

   private void newSymbolicArray(final Context ctx, final InitStrategy initStrategy, final ISymbol arrayLength) {
      final Object arrayAddress = ctx.newObject(new Allocatable() {
         @Override public int allocateSize() {
            return NewArrayOp.ARRAY_PREAMBLE + 1;
         }
      });
      NewConcArray.initArrayPreamble(ctx, arrayAddress, arrayLength);
      // TODO[tim]: support other kinds of arrays
      ctx.put(arrayAddress, ARRAY_SYMBOL_OFFSET, new IArrayZeroedSymbol());
      ctx.push(arrayAddress);
   }

   private void newConcreteArray(final Context ctx, final InitStrategy initStrategy, final int arrayLength) {
      new NewConcArray().newConcreteArray(ctx, arrayLength, initStrategy);
   }
}
