package com.lexicalscope.symb.vm;

public interface FlowNode<S> {
	S state();
	void eval();
}
