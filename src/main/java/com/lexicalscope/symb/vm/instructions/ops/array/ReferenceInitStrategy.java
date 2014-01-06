package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Heap;

final class ReferenceInitStrategy implements InitStrategy {
   @Override public Object initialValue(final Heap heap) {
      return heap.nullPointer();
   }
}