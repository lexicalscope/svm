package com.lexicalscope.symb.vm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public final class Vm<S> {
	final Deque<FlowNode<S>> pending = new ArrayDeque<>();
	final Deque<FlowNode<S>> finished = new ArrayDeque<>();

	public Vm(final FlowNode<S> state) {
		pending.push(state);
	}

	public FlowNode<S> execute() {
		while (!pending.isEmpty()) {
			try {
				pending.peek().eval(this);
			} catch (final TerminationException termination) {
				finished.push(pending.pop());
			} catch (final RuntimeException e) {
				throw e;
			}
		}
		return result();
	}

	public void fork(final FlowNode<S>[] states) {
		pending.pop();

		for (final FlowNode<S> state : states) {
			pending.push(state);
		}
	}

	public FlowNode<S> result() {
		return finished.peek();
	}

	public Collection<FlowNode<S>> results() {
		return finished;
	}

	public S state() {
		return pending.peek().state();
	}
}
