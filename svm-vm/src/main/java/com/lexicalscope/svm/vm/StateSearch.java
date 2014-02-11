package com.lexicalscope.svm.vm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

class StateSearch<S> {
   final Deque<FlowNode<S>> pending = new ArrayDeque<>();
   final Deque<FlowNode<S>> finished = new ArrayDeque<>();

   public FlowNode<S> firstResult() {
      return finished.isEmpty() ? null : finished.peek();
   }

   public void reachedLeaf() {
      finished.push(pending.pop());
   }

   public FlowNode<S> pendingState() {
      return pending.peek();
   }

   public boolean searching() {
      return !pending.isEmpty();
   }

   public void initial(final FlowNode<S> state) {
      pending.push(state);
   }

   public void fork(final FlowNode<S>[] states) {
      pending.pop();

      for (final FlowNode<S> state : states) {
         pending.push(state);
      }
   }

   public Collection<FlowNode<S>> results() {
      return finished;
   }
}