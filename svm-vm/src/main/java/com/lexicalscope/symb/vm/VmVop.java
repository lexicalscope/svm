package com.lexicalscope.symb.vm;

public interface VmVop<S extends ExecutableState<S>> {
	Void eval(Vm<S> vm, S state);
}
