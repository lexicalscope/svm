package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.heap.FastHeap;
import com.lexicalscope.svm.heap.Heap;
import com.lexicalscope.svm.heap.HeapFactory;
import com.lexicalscope.svm.vm.conc.checkingheap.CheckingHeap;

public class CheckingSymbolicHeapFactory implements HeapFactory {
   @Override public Heap heap() {
      return new CheckingHeap(new FastHeap(), new SymbolicHeapCheck());
   }
}
