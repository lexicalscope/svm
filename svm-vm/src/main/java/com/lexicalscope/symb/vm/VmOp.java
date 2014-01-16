package com.lexicalscope.symb.vm;

public interface VmOp<T, S extends ExecutableState<S>> {
	T eval(Vm<S> vm, S state);
}
