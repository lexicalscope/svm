package com.lexicalscope.symb.vm.symbinstructions.ops;

import static com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp.ARRAY_LENGTH_OFFSET;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayLoadOp;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

public class SArrayLoadOp implements Vop {
   private final FeasibilityChecker feasibilityChecker;
   private final ArrayLoadOp concreteArrayLoad;

   public SArrayLoadOp(final FeasibilityChecker feasibilityChecker, final ArrayLoadOp concreteArrayLoad) {
      this.feasibilityChecker = feasibilityChecker;
      this.concreteArrayLoad = concreteArrayLoad;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object offset = (int) stackFrame.pop();
      final Object arrayref = stackFrame.pop();

      final Object arrayLength = heap.get(arrayref, ARRAY_LENGTH_OFFSET);
      if(arrayLength instanceof Integer && offset instanceof Integer) {
         stackFrame.push(concreteArrayLoad.loadFromHeap(heap, arrayref, (int) offset));
      } else if (arrayLength instanceof ISymbol) {
         loadFromSymbolicArray(heap, arrayref, offset);
      } else {
         throw new UnsupportedOperationException("concrete array symbolic offset");
      }
   }

   private void loadFromSymbolicArray(final Heap heap, final Object arrayref, final Object offset) {
      throw new UnsupportedOperationException("load from symbolic array");
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
