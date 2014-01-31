package com.lexicalscope.symb.vm.conc;

import com.lexicalscope.symb.heap.FastHeap;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.heap.HeapFactory;

public class FastHeapFactory implements HeapFactory {
   @Override public Heap heap() {
      return new FastHeap();
   }
}
