package com.lexicalscope.svm.vm;

import java.util.Collection;

public final class VmImpl<S extends VmState> implements Vm<S> {
   private final StateSearch<S> search;

	public VmImpl(final StateSearch<S> search) {
      this.search = search;
   }

   @Override
	public S execute() {
      int count = 0;
      S pending;
      while ((pending = search.pendingState()) != null && count < 50000) {
			try {
			   pending.eval();
			} catch (final TerminationException termination) {
			   search.reachedLeaf();
			} catch (final RuntimeException e) {
				throw e;
			}
			count++;
		}
      if(!(count<50000)) {
         System.out.println("limit reached");
         System.out.println(search.results());
      }
		return search.firstResult();
	}

	@Override
	public S result() {
		return search.firstResult();
	}

	@Override
	public Collection<S> results() {
		return search.results();
	}
}
