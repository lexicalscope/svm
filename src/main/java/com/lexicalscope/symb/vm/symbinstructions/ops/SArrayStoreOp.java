package com.lexicalscope.symb.vm.symbinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayStoreOp;
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
      throw new UnsupportedOperationException("symbolic array");
   }

   @Override public String toString() {
      return "ARRAYSTORE";
   }
   public static Vop aaStore(final FeasibilityChecker feasibilityChecker) {
      return new SArrayStoreOp(feasibilityChecker, ArrayStoreOp.aaStore());
   }
}
