package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.heap.FastHeap;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.heap.HeapFactory;
import com.lexicalscope.symb.vm.conc.checkingheap.CheckingHeap;

public class CheckingSymbolicHeapFactory implements HeapFactory {
   @Override public Heap heap() {
      return new CheckingHeap(new FastHeap(), new SymbolicHeapCheck());
   }
}
