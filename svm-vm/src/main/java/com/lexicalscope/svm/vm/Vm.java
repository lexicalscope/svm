package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface Vm<S extends VmState> {
	S execute();

	void initial(S state);

	void fork(S[] states);
	void goal();

	S result();

	Collection<S> results();

   S pending();
}