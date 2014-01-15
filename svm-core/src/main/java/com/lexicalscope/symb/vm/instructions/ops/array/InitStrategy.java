package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.heap.Heap;

public interface InitStrategy {
   Object initialValue(Heap heap);
}