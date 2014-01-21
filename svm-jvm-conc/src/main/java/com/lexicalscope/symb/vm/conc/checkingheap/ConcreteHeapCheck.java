package com.lexicalscope.symb.vm.conc.checkingheap;

public class ConcreteHeapCheck implements HeapCheck {
	@Override
	public boolean allowedInIntField(final Object val) {
		return val instanceof Integer;
	}
}
