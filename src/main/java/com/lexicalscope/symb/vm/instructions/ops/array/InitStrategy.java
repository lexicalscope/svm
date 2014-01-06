package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.vm.Heap;

public interface InitStrategy {
   Object initialValue(Heap heap);
}