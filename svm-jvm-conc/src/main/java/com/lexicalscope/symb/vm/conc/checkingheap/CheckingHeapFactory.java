package com.lexicalscope.symb.vm.conc.checkingheap;

import com.lexicalscope.symb.heap.FastHeap;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.heap.HeapFactory;

public class CheckingHeapFactory implements HeapFactory {
	@Override
	public Heap heap() {
		return new CheckingHeap(new FastHeap(), new ConcreteHeapCheck());
	}
}
