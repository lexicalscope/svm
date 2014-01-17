package com.lexicalscope.symb.vm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public final class VmImpl<S> implements Vm<S> {
	final Deque<FlowNode<S>> pending = new ArrayDeque<>();
	final Deque<FlowNode<S>> finished = new ArrayDeque<>();

	@Override
	public FlowNode<S> execute() {
		while (!pending.isEmpty()) {
			try {
				pending.peek().eval();
			} catch (final TerminationException termination) {
				finished.push(pending.pop());
			} catch (final RuntimeException e) {
				throw e;
			}
		}
		return finished.isEmpty() ? null : result();
	}

	@Override
	public void initial(final FlowNode<S> state) {
		pending.push(state);
	}

	@Override
	public void fork(final FlowNode<S>[] states) {
		pending.pop();

		for (final FlowNode<S> state : states) {
			pending.push(state);
		}
	}

	@Override
	public FlowNode<S> result() {
		return finished.peek();
	}

	@Override
	public Collection<FlowNode<S>> results() {
		return finished;
	}
}
