package com.lexicalscope.svm.vm.conc.checkingheap;

import com.lexicalscope.svm.heap.FastHeap;
import com.lexicalscope.svm.heap.Heap;
import com.lexicalscope.svm.heap.HeapFactory;

public class CheckingHeapFactory implements HeapFactory {
	@Override
	public Heap heap() {
		return new CheckingHeap(FastHeap.createFastHeap(), new ConcreteHeapCheck());
	}
}
