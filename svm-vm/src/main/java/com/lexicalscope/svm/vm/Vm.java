package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface Vm<S> {
	FlowNode<S> execute();

	void initial(FlowNode<S> state);

	void fork(FlowNode<S>[] states);

	FlowNode<S> result();

	Collection<FlowNode<S>> results();

   FlowNode<S> pending();
}