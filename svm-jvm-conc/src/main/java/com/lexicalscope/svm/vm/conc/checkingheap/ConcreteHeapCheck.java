package com.lexicalscope.svm.vm.conc.checkingheap;

public class ConcreteHeapCheck implements HeapCheck {
	@Override
	public boolean allowedInIntField(final Object val) {
		return val instanceof Integer;
	}
}
