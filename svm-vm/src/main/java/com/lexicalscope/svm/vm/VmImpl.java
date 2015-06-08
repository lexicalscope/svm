package com.lexicalscope.svm.vm;

import java.util.Collection;

public final class VmImpl<S extends VmState> implements Vm<S> {
   private final StateSearch<S> search;
   private SearchLimits limits;

	public VmImpl(final StateSearch<S> search) {
      this(search, new StateCountSearchLimit());
   }

	public VmImpl(final StateSearch<S> search, final SearchLimits limits) {
	   this.search = search;
      this.limits = limits;
	}

   @Override
	public S execute() {
	   limits.reset();
      S pending;
      int j = 0;
      while ((pending = search.pendingState()) != null && limits.withinLimits()) {
			try {
			   pending.eval();
			   j++;
			} catch (final TerminationException termination) {
			   search.reachedLeaf();
			}
//			catch (final RuntimeException e) {
//				throw e;
//			}
			limits.searchedState();
		}
      System.out.println("blah " + j);
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
