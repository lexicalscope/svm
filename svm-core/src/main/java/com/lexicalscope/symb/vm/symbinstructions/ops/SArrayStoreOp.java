package com.lexicalscope.symb.vm.symbinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;
import static com.lexicalscope.symb.vm.symbinstructions.PcBuilder.asISymbol;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
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

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object value = stackFrame.pop();
      final Object offset = stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      final Object arrayLength = heap.get(arrayref, ARRAY_LENGTH_OFFSET);
      if(arrayLength instanceof Integer && offset instanceof Integer) {
         concreteArrayStore.storeInHeap(heap, arrayref, (int) offset, value);
      } else if (arrayLength instanceof ISymbol) {
         storeInSymbolicArray(heap, arrayref, offset, value);
      } else {
         throw new UnsupportedOperationException("concrete array symbolic offset");
      }
   }

   private void storeInSymbolicArray(final Heap heap, final Object arrayref, final Object offset, final Object value) {
      final IArraySymbol symbol = (IArraySymbol) heap.get(arrayref, NewSymbArray.ARRAY_SYMBOL_OFFSET);
      heap.put(arrayref,
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
