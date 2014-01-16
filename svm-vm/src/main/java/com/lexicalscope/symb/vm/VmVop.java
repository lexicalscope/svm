package com.lexicalscope.symb.vm;

public interface VmVop<S extends VmVop<S>> {
	void eval(Vm<S> vm);
}
