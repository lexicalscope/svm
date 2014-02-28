package com.lexicalscope.svm.vm;

import java.util.Collection;

public final class VmImpl<S> implements Vm<S> {
   private final StateSearch<S> search;

	public VmImpl(final StateSearch<S> search) {
      this.search = search;
   }

   @Override
	public FlowNode<S> execute() {
      FlowNode<S> pending;
      while ((pending = search.pendingState()) != null) {
			try {
			   pending.eval();
			} catch (final TerminationException termination) {
			   search.reachedLeaf();
			} catch (final RuntimeException e) {
				throw e;
			}
		}
		return search.firstResult();
	}

   @Override public void initial(final FlowNode<S> state) {
      search.initial(state);
   }

	@Override
	public void fork(final FlowNode<S>[] states) {
		search.fork(states);
	}

   @Override public void goal() {
      search.goal();
   }

	@Override
	public FlowNode<S> result() {
		return search.firstResult();
	}

	@Override
	public Collection<FlowNode<S>> results() {
		return search.results();
	}

   @Override public FlowNode<S> pending() {
      return search.pendingState();
   }
}
