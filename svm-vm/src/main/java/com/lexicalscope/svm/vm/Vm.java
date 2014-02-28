package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface Vm<S extends VmState> {
	S execute();

	S result();

	Collection<S> results();
}