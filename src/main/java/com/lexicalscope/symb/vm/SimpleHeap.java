package com.lexicalscope.symb.vm;

public class SimpleHeap implements Heap {
	@Override
   public Heap snapshot() {
		return new SimpleHeap();
	}
}
