package com.lexicalscope.symb.vm.symbinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;
import static com.lexicalscope.symb.vm.symbinstructions.PcBuilder.asISymbol;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayLoadOp;
import com.lexicalscope.symb.vm.symbinstructions.ops.array.NewSymbArray;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArraySelectSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IArraySymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class SArrayLoadOp implements Vop {
   private final FeasibilityChecker feasibilityChecker;
   private final ArrayLoadOp concreteArrayLoad;

   public SArrayLoadOp(final FeasibilityChecker feasibilityChecker, final ArrayLoadOp concreteArrayLoad) {
      this.feasibilityChecker = feasibilityChecker;
      this.concreteArrayLoad = concreteArrayLoad;
   }

   @Override public void eval(final State ctx) {
      final Object offset = (int) ctx.pop();
      final Object arrayref = ctx.pop();

      final Object arrayLength = ctx.get(arrayref, ARRAY_LENGTH_OFFSET);
      if(arrayLength instanceof Integer && offset instanceof Integer) {
         ctx.push(concreteArrayLoad.loadFromHeap(ctx, arrayref, (int) offset));
      } else if (arrayLength instanceof ISymbol) {
         ctx.push(loadFromSymbolicArray(ctx, arrayref, offset));
      } else {
         throw new UnsupportedOperationException("concrete array symbolic offset");
      }
   }

   private Object loadFromSymbolicArray(final State ctx, final Object arrayref, final Object offset) {
      return new IArraySelectSymbol(
            (IArraySymbol) ctx.get(arrayref, NewSymbArray.ARRAY_SYMBOL_OFFSET),
            asISymbol(offset));
   }

   @Override public String toString() {
      return "ARRAYLOAD";
   }

   public static Vop iaLoad(final FeasibilityChecker feasibilityChecker) {
      return new SArrayLoadOp(feasibilityChecker, ArrayLoadOp.iaLoad());
   }

   public static Vop aaLoad(final FeasibilityChecker feasibilityChecker) {
      return new SArrayLoadOp(feasibilityChecker, ArrayLoadOp.aaLoad());
   }
}
