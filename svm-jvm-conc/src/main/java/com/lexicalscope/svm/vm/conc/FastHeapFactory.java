package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.heap.FastHeap;
import com.lexicalscope.svm.heap.Heap;
import com.lexicalscope.svm.heap.HeapFactory;

public class FastHeapFactory implements HeapFactory {
   @Override public Heap heap() {
      return new FastHeap();
   }
}
