package com.lexicalscope.symb.vm.symbinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;
import static com.lexicalscope.symb.vm.symbinstructions.PcBuilder.asISymbol;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayStoreOp;
import com.lexicalscope.symb.vm.symbinstructions.ops.array.NewSymbArray;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArrayStoreSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArraySymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class SArrayStoreOp implements Vop {
   private final FeasibilityChecker feasibilityChecker;
   private final ArrayStoreOp concreteArrayStore;

   public SArrayStoreOp(final FeasibilityChecker feasibilityChecker, final ArrayStoreOp concreteArrayStore) {
      this.feasibilityChecker = feasibilityChecker;
      this.concreteArrayStore = concreteArrayStore;
   }

   @Override public void eval(final StateImpl ctx) {
      final Object value = ctx.pop();
      final Object offset = ctx.pop();
      final Object arrayref = ctx.pop();

      final Object arrayLength = ctx.get(arrayref, ARRAY_LENGTH_OFFSET);
      if(arrayLength instanceof Integer && offset instanceof Integer) {
         concreteArrayStore.storeInHeap(ctx, arrayref, (int) offset, value);
      } else if (arrayLength instanceof ISymbol) {
         storeInSymbolicArray(ctx, arrayref, offset, value);
      } else {
         throw new UnsupportedOperationException("concrete array symbolic offset");
      }
   }

   private void storeInSymbolicArray(final State ctx, final Object arrayref, final Object offset, final Object value) {
      final IArraySymbol symbol = (IArraySymbol) ctx.get(arrayref, NewSymbArray.ARRAY_SYMBOL_OFFSET);
      ctx.put(arrayref,
            NewSymbArray.ARRAY_SYMBOL_OFFSET,
            new IArrayStoreSymbol(symbol, asISymbol(offset), asISymbol(value)));
   }

   @Override public String toString() {
      return "ARRAYSTORE";
   }

   public static Vop aaStore(final FeasibilityChecker feasibilityChecker) {
      return new SArrayStoreOp(feasibilityChecker, ArrayStoreOp.aaStore());
   }

   public static Vop iaStore(final FeasibilityChecker feasibilityChecker) {
      return new SArrayStoreOp(feasibilityChecker, ArrayStoreOp.iaStore());
   }
}
