package com.lexicalscope.svm.vm;

public interface FlowNode<S> {
	S state();
	void eval();
}
